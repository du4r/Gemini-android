package com.amine.geminidroid.camera

import android.app.Activity
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amine.geminidroid.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class CameraViewModel(activity: Activity) : ViewModel() {

    var responseText: MutableState<String> = mutableStateOf("")
    var photoUri: MutableState<Uri> = mutableStateOf(Uri.EMPTY)


    private val contentResolver: ContentResolver = activity.contentResolver


    val generativeModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.GAS_KEY
    )

    @RequiresApi(Build.VERSION_CODES.P)
    fun recognize(uri: Uri) {
        viewModelScope.launch {
            val source = ImageDecoder.createSource(contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)

            val inputContent = content {
                image(resizedBitmap)
                text("descreva a imagem")
            }
            Log.d("braum", "teste")
            try {
                val response = generativeModel.generateContent(inputContent)
                responseText.value = response.text.toString()
            } catch (e: Exception) {
                Log.d("edbug", e.message.toString())
            }

        }
    }


}