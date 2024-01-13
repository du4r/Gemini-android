package com.amine.geminidroid.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amine.geminidroid.BuildConfig

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatViewmodel: ViewModel(){

    val response = MutableStateFlow<String>("")

    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GAS_KEY
    )

    fun Ask(question: String){
        viewModelScope.launch {
            val prompt = generativeModel.generateContent(question)
            response.value = prompt.text.toString()
        }
    }

}