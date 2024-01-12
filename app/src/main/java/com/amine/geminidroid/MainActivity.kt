package com.amine.geminidroid

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amine.geminidroid.camera.CameraView
import com.amine.geminidroid.camera.CameraViewModel
import com.amine.geminidroid.camera.ResultScreen
import com.amine.geminidroid.text.AskViewmodel
import com.amine.geminidroid.ui.theme.GeminiDroidTheme
import java.io.File
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {


    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

    private lateinit var photoUri: Uri
    lateinit var cameraViewModel: CameraViewModel
    lateinit var askMeViewmodel: AskViewmodel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cameraViewmodelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CameraViewModel(this@MainActivity) as T
            }
        }

        val askMeViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AskViewmodel() as T
            }
        }

        cameraViewModel =
            ViewModelProvider(this, cameraViewmodelFactory).get(cameraViewModel::class.java)
        askMeViewmodel =
            ViewModelProvider(this, askMeViewModelFactory).get(askMeViewmodel::class.java)

        setContent {
            GeminiDroidTheme {
                when {
                    shouldShowCamera.value -> CameraView(outputDirectory = ::getOutputDirectory.invoke(),
                        executor = Executors.newSingleThreadExecutor(),
                        onImageCaptured = ::handleImageCapture,
                        onError = { Log.e("edbug", "view error:", it) }, cameraViewModel
                    )

                    shouldShowPhoto.value -> ResultScreen(photoUri, cameraViewModel)
                }
            }
        }
        requestCameraPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("edbug", "Permission is granted")
            shouldShowCamera.value = true
        } else {
            Log.i("edbug", "Permission is not granted")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("edbug", "permission is granted ")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.d("edbug", "show camera permission dialog")

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    fun handleImageCapture(uri: Uri) {
        Log.i("edbug", "Image captured: $uri")
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
    }
}
