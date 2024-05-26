// FloatingWidget.kt
package com.example.noctuapp

import android.content.Context
import android.graphics.PixelFormat
import android.os.AsyncTask
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun FloatingChatbot(context: Context? = null) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMusic by remember { mutableStateOf<String?>(null) }
    var selectedAttendance by remember { mutableStateOf<String?>(null) }
    var response by remember { mutableStateOf("Elige las opciones que quieras") }

    Column(
        modifier = Modifier
            .width(200.dp)
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "Help",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(10.dp)
        )
        if (expanded) {
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
                        context?.let {
                            FetchPlacesTask(it, selectedMusic!!, selectedAttendance!!) { result ->
                                response = result
                            }.execute()
                        } ?: run {
                            response = "Context is null"
                        }
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
    }
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

class FetchPlacesTask(
    private val context: Context,
    private val musicType: String,
    private val attendance: String,
    private val callback: (String) -> Unit
) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val url = URL("http://your-ngrok-url/your_php_script.php")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

        val jsonInputString = JSONObject()
            .put("musicType", musicType)
            .put("attendance", attendance)
            .toString()

        connection.outputStream.use { os ->
            val input = jsonInputString.toByteArray()
            os.write(input, 0, input.size)
        }

        val response = StringBuilder()
        BufferedReader(InputStreamReader(connection.inputStream)).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                response.append(line?.trim())
            }
        }

        val jsonResponse = JSONArray(response.toString())
        if (jsonResponse.length() == 0) {
            return "No se encontraron lugares"
        }

        val places = mutableListOf<String>()
        for (i in 0 until jsonResponse.length()) {
            places.add(jsonResponse.getJSONObject(i).getString("name"))
        }

        return "Lugares: ${places.joinToString(", ")}"
    }

    override fun onPostExecute(result: String) {
        callback(result)
    }
}

fun showFloatingChatbot(context: Context) {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    val composeView = ComposeView(context).apply {
        setContent {
            MaterialTheme {
                Surface {
                    FloatingChatbot(context)
                }
            }
        }
    }

    windowManager.addView(composeView, layoutParams)
}

@Preview(showBackground = true)
@Composable
fun FloatingChatbotPreviewCollapsed() {
    FloatingChatbot()
}

@Preview(showBackground = true)
@Composable
fun FloatingChatbotPreviewExpanded() {
    FloatingChatbot()
}

@Composable
fun FloatingChatbotExpanded() {
    var query by remember { mutableStateOf("Reggaeton en Málaga") }
    var response by remember { mutableStateOf("¿Qué buscas?") }

    Column(
        modifier = Modifier
            .width(200.dp)
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "Chatbot",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .padding(10.dp)
        )
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            response = "Lugares: Rosse, K2, Sala Gold"
        }) {
            Text("Enviar")
        }
        Text(response)
    }
}
