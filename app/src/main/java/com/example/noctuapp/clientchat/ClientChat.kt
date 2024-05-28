package com.example.noctuapp.clientchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noctuapp.getEmpresasFromPHP
import com.example.noctuapp.ui.theme.NoctuappTheme
import com.example.noctuapp.ui.theme.noctuapp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaChat() {
    val coroutineScope = rememberCoroutineScope()
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf(Message("¿Quieres encontrar tu sitio ideal? Has venido al sitio correcto.", isUser = false))) }

    NoctuappTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            MessagesList(messages = messages, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(80.dp)) // Añadir espacio de 80dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 80.dp) // Padding personalizado
            ) {
                OutlinedTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    placeholder = { Text("Escribe un mensaje...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        cursorColor = noctuapp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {

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
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = noctuapp)
                ) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
fun MessagesList(messages: List<Message>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
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
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser) noctuapp else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(4.dp)
                .shadow(10.dp, shape = MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (message.isUser) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = message.text,
                    color = if (message.isUser) Color.White else Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

data class Message(val text: String, val isUser: Boolean)

@Preview(showBackground = true)
@Composable
fun VistaChatPreview() {
    VistaChat()
}