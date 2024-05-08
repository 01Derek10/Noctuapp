package com.example.noctuapp.login


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.R
import com.example.noctuapp.elements.TransparentTextField
import com.example.noctuapp.ui.theme.NoctuappTheme
import kotlinx.coroutines.launch


@Composable
fun VistaLogin(navController: NavController) {

    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var datosError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var containerColorUser by remember { mutableStateOf(Color.Black) }
    var containerColorPass by remember { mutableStateOf(Color.Black) }
    val context = LocalContext.current
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
                CircularProgressIndicator(color = Color.Black)
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
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription ="logo", modifier = Modifier.size(200.dp) )
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
                colors =ButtonDefaults.buttonColors(Color.Black),
                onClick = {
                    coroutineScope.launch {
                        try {
                            var formIsValid = true

                            if (username.value.isEmpty()) {
                                containerColorUser = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorUser = Color.Black
                            }

                            if (password.value.isEmpty()) {
                                containerColorPass = Color(0xFFFE0000)
                                formIsValid = false
                            } else {
                                containerColorPass = Color.Black
                            }

                            if (formIsValid) {
                                // aqui tendria que hacer la llamada a la comprobacion en la bbdd,
                                //SI LOS DATOS SON CORRECTOS TE HACE LA NAVEGACION A LA SIGUEINTE PESTAÑA SINO SALTA EL ERROR
                                Log.i("login","todo bien")
                                navController.navigate(route = selectNavegation.Lugares.route)

                            }
                        } catch (e: Exception) {
                            Log.e("ButtonOnClick", "Error al realizar el login: ${e.message}")
                            datosError = true // Mostrar el diálogo de error si ocurre una excepción
                        }
                    }
                }

            ){
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
    if(datosError){
        AlertDialog(
            onDismissRequest = {
                datosError=false
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        // Acción para el botón de confirmar
                        datosError=false
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
        FloatingActionButton(
            modifier = Modifier
                .size(72.dp)
                .background(Color.Transparent),
            containerColor = Color.Black ,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            onClick = {
                            }

        ) {

        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaLoginPreview() {
    NoctuappTheme {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        var containerColorUser by remember { mutableStateOf(Color.Black) }
        var containerColorPass by remember { mutableStateOf(Color.Black) }



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
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "logo",
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.size(60.dp))

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
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    onClick = {
                        //Logica boton
                        var formIsValid = true

                        if (username.value.isEmpty()) {
                            containerColorUser = Color(0xFF8B0000)
                            formIsValid = false
                        } else {
                            containerColorUser = Color.Black
                        }
                        if (password.value.isEmpty()) {
                            containerColorPass = Color(0xFF8B0000)
                            formIsValid = false
                        } else {
                            containerColorPass = Color.Black
                        }

                        if (formIsValid) {



                        }
                    }

                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }


        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom

        ) {
             {

            }
        }
    }
}