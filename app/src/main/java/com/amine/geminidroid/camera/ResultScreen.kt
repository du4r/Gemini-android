package com.amine.geminidroid.camera

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ResultScreen(photoUri: Uri, viewModel: CameraViewModel) {

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(photoUri),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (viewModel.responseText.value.isBlank()) {
                CircularProgressIndicator()
            } else {
                Text(text = viewModel.responseText.value)
                Log.i("desnecessauro", viewModel.responseText.value)
            }
        }

        LaunchedEffect(key1 = photoUri){
                viewModel.recognize(photoUri)
        }
}