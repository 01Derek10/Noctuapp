package com.example.noctuapp.perfil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.R
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.ui.theme.NoctuappTheme
import com.example.noctuapp.ui.theme.noctuapp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaPerfil(navController: NavController, bottomAppBar: BottomAppBar) {
    val name = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val age = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var showProgressDialog by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf(false) }

    if (showProgressDialog) {
        Dialog(onDismissRequest = { showProgressDialog = false }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(color = noctuapp)
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar().BottomBar(navController,2)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Photo section
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(noctuapp, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_simple),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(110.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Name input field
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = noctuapp
                    )
                )

                // Last Name input field
                OutlinedTextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    label = { Text("Apellidos") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = noctuapp
                    )
                )

                // Age input field
                OutlinedTextField(
                    value = age.value,
                    onValueChange = { age.value = it },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = noctuapp
                    )
                )

                // Email input field
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = noctuapp
                    )
                )

                // Description input field
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    visualTransformation = VisualTransformation.None,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = noctuapp,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = noctuapp
                    )
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            showProgressDialog = true
                            val success = updateUserData(name.value, email.value)
                            showProgressDialog = false
                            if (!success) {
                                saveError = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(noctuapp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Guardar", color = Color.White)
                }
            }
        }
    )

    if (saveError) {
        AlertDialog(
            onDismissRequest = {
                saveError = false
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = noctuapp
                    ),
                    onClick = {
                        saveError = false
                    }
                ) {
                    Text("Salir", color = Color.White)
                }
            },
            title = {
                Text(text = "Error al guardar los datos")
            },
            text = {
                Text("No se pudo guardar los datos. Por favor, intente de nuevo.")
            },
        )
    }
}

suspend fun updateUserData(username: String, email: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://192.168.1.148/update.php") // Cambia esto a la URL de tu servidor
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val requestBody = "username=$username&email=$email"
            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(requestBody)
            outputStreamWriter.flush()

            val responseCode = connection.responseCode
            val responseMessage = connection.inputStream.bufferedReader().readText()
            connection.disconnect()

            Log.d("HTTP_RESPONSE", "Response Code: $responseCode, Response: $responseMessage")

            responseCode == 200 && responseMessage.contains("\"status\":\"success\"")
        } catch (e: Exception) {
            Log.e("HTTP_ERROR", "Error during HTTP request: ${e.message}")
            false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPerfil() {
    NoctuappTheme {
        VistaPerfil(navController = rememberNavController(), bottomAppBar = BottomAppBar())
    }
}
