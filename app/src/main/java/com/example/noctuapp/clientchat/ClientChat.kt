package com.example.noctuapp.clientchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noctuapp.getEmpresasFromPHP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun VistaChat() {
    val coroutineScope = rememberCoroutineScope()
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf(Message("¿Quieres encontrar tu sitio ideal? Has venido al sitio correcto.", isUser = false))) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(bottom = 80.dp)
        ) {}
        MessagesList(messages = messages, modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp, bottom = 80.dp)
        ) {
            BasicTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Button(onClick = {
                coroutineScope.launch {
                    if (userMessage.text.isNotBlank()) {
                        val empresas = withContext(Dispatchers.IO) {
                            getEmpresasFromPHP()
                        }
                        messages = messages + Message(userMessage.text, isUser = true)
                        val botResponse = NoctuBot.getResponse(userMessage.text, empresas)
                        messages = messages + Message(botResponse, isUser = false)
                        userMessage = TextFieldValue("") // Clear the input field
                    }
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessagesList(messages: List<Message>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        messages.forEach { message ->
            MessageItem(message)
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = message.text,
            color = if (message.isUser) Color.Blue else Color.Black,
            modifier = Modifier
                .background(if (message.isUser) Color.Cyan else Color.LightGray)
                .padding(8.dp)
        )
    }
}

data class Message(val text: String, val isUser: Boolean)