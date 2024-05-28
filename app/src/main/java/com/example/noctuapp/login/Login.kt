package com.example.noctuapp.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.R
import com.example.noctuapp.elements.TransparentTextField
import com.example.noctuapp.ui.theme.NoctuappTheme

import com.example.noctuapp.ui.theme.noctuapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun VistaLogin(navController: NavController) {
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var datosError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    var containerColorUser by remember { mutableStateOf(noctuapp) }
    var containerColorPass by remember { mutableStateOf(noctuapp) }

    val coroutineScope = rememberCoroutineScope()
    var showProgressDialog by remember { mutableStateOf(false) }

    if (showProgressDialog) {
        Dialog(onDismissRequest = { showProgressDialog = false }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(color = Color(0xFF800080)) // Color morado
            }
        }
    }


    suspend fun registerUser(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://192.168.1.148/register.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val requestBody = "username=$username&password=$password"
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

    suspend fun checkCredentials(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://192.168.1.148/login.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val requestBody = "username=$username&password=$password"
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(

                painter = painterResource(id = R.drawable.logo_con_nombre),

                contentDescription = "logo",
                modifier = Modifier.size(200.dp)
            )
        }
        TransparentTextField(
            textFieldValue = username,
            textLabel = stringResource(R.string.usuario),
            keyboardType = KeyboardType.Email,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            imeAction = ImeAction.Next,
            containerColor = containerColorUser
        )
        TransparentTextField(
            textFieldValue = password,
            textLabel = stringResource(R.string.contraseña),
            keyboardType = KeyboardType.Password,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            ),
            imeAction = ImeAction.Done,
            containerColor = containerColorPass,
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = "Ocultar contraseña"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(50),

                colors = ButtonDefaults.buttonColors(noctuapp),

                onClick = {
                    coroutineScope.launch {
                        try {
                            var formIsValid = true

                            if (username.value.isEmpty()) {
                                containerColorUser = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorUser = noctuapp
                            }

                            if (password.value.isEmpty()) {
                                containerColorPass = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorPass = noctuapp
                            }

                            if (formIsValid) {
                                showProgressDialog = true
                                val success = checkCredentials(username.value, password.value)
                                showProgressDialog = false
                                if (success) {
                                    navController.navigate(route = selectNavegation.Lugares.route)
                                } else {
                                    datosError = true
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("ButtonOnClick", "Error al realizar el login: ${e.message}")
                            datosError = true // Mostrar el diálogo de error si ocurre una excepción
                        }
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            Button(
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(noctuapp),
                onClick = {
                    coroutineScope.launch {
                        try {
                            var formIsValid = true

                            if (username.value.isEmpty()) {
                                containerColorUser = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorUser = noctuapp
                            }

                            if (password.value.isEmpty()) {
                                containerColorPass = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorPass = noctuapp
                            }

                            if (formIsValid) {
                                showProgressDialog = true
                                val success = registerUser(username.value, password.value)
                                showProgressDialog = false
                                if (success) {
                                    navController.navigate(route = selectNavegation.Lugares.route)
                                } else {
                                    datosError = true
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("ButtonOnClick", "Error al realizar el registro: ${e.message}")
                            datosError = true // Mostrar el diálogo de error si ocurre una excepción
                        }
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
    if (datosError) {
        AlertDialog(
            onDismissRequest = {
                datosError = false
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = noctuapp
                    ),
                    onClick = {
                        // Acción para el botón de confirmar
                        datosError = false
                    }
                ) {
                    Text("Salir")
                }
            },
            title = {
                Text(text = "Error al iniciar sesión")
            },
            text = {
                Text("Ponga de nuevo los valores introducidos")
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewVistaLogin() {
    NoctuappTheme {
        // Simulamos un NavController
        val navController = rememberNavController()
        VistaLogin(navController = navController)
    }
}

