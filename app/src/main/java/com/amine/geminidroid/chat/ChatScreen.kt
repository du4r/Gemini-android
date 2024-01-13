package com.amine.geminidroid.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AskMe(viewmodel: ChatViewmodel){

    val keyboadController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val searchText = remember { mutableStateOf("") }
    val messages = remember { mutableStateOf(mutableListOf<String>()) }

    Scaffold { paddingValues ->
        PaddingValues(16.dp)
     Column (
         modifier = Modifier
             .padding(paddingValues)
             .fillMaxSize(),
         verticalArrangement = Arrangement.Bottom
     ){

         LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(messages.value){ message ->
                Text(text = message)
            }
         }

         Row(
             modifier = Modifier.padding(8.dp),
             verticalAlignment = Alignment.Bottom,
         ) {
             OutlinedTextField(
                 modifier = Modifier
                     .clickable(
                         onClick = { keyboadController?.show() }
                     )
                     .weight(1f)
                     .focusRequester(focusRequester),
                 value = searchText.value,
                 onValueChange = { searchText.value = it },
                 label = { Text(text = "Pergunte algo...") },
             )
             IconButton(
                 onClick = {
                     messages.value.add(searchText.value)
                 }
             ) {
                 Icon(Icons.Default.Send, contentDescription = "Search")
             }
         }

         
        }
    }
    
    LaunchedEffect(key1 = messages.value){
        viewmodel.Ask(searchText.value)
    }
}

@Composable
@Preview
fun AskMePreview(){
    AskMe(viewmodel = ChatViewmodel())
}
