package com.example.noctuapp.chatbot

import android.content.Context
import android.graphics.PixelFormat
import android.os.AsyncTask
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun FloatingChatbotDialog(onDismiss: () -> Unit) {
    var selectedMusic by remember { mutableStateOf<String?>(null) }
    var selectedAttendance by remember { mutableStateOf<String?>(null) }
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
                MusicButton("Reggaeton", selectedMusic) { selectedMusic = it }
                MusicButton("Salsa", selectedMusic) { selectedMusic = it }
                MusicButton("Flamenco", selectedMusic) { selectedMusic = it }

                Text(
                    text = "Concurrencia:",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                AttendanceButton("Baja", selectedAttendance) { selectedAttendance = it }
                AttendanceButton("Media", selectedAttendance) { selectedAttendance = it }
                AttendanceButton("Alta", selectedAttendance) { selectedAttendance = it }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (selectedMusic != null && selectedAttendance != null) {
                            // Aquí debes hacer la llamada a tu API
                            response = "Lugares recomendados:..."
                        } else {
                            response = "Por favor selecciona ambas opciones"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Buscar")
                }
                Text(response)
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

@Composable
fun AttendanceButton(text: String, selected: String?, onSelect: (String) -> Unit) {
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
