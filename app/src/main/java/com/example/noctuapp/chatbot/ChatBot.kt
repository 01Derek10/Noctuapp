package com.example.noctuapp.chatbot


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noctuapp.lugares.LugaresFilteredByMusic
import com.example.noctuapp.lugares.LugaresRepository
import com.example.noctuapp.lugares.PartyPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FloatingChatbotDialog(
    onDismiss: () -> Unit,
    lugaresRepository: LugaresRepository,
    navController: NavController
) {
    var selectedMusic by remember { mutableStateOf<String?>(null) }
    var response by remember { mutableStateOf("Elige las opciones que quieras") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Help")
        },
        text = {
            Column {
                Text(
                    text = "Tipo de Música:",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(Modifier.horizontalScroll(rememberScrollState())) {
                    MusicButton("Reggeton", selectedMusic) { selectedMusic = it }
                    MusicButton("Trap", selectedMusic) { selectedMusic = it }
                    MusicButton("Pop", selectedMusic) { selectedMusic = it }
                    MusicButton("Jazz", selectedMusic) { selectedMusic = it }
                    MusicButton("Rock", selectedMusic) { selectedMusic = it }
                    MusicButton("Blues", selectedMusic) { selectedMusic = it }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(response)

                // Mostrar los lugares filtrados
                selectedMusic?.let { music ->
                    LugaresFilteredByMusic(
                        lugaresRepository = lugaresRepository,
                        selectedMusic = music,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cerrar")
            }
        }

    )
}



@Composable
fun MusicButton(text: String, selected: String?, onSelect: (String) -> Unit) {
    Button(
        onClick = { onSelect(text) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == text) Color.Blue else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(2.dp)
    ) {
        Text(text, color = Color.White)
    }
}

