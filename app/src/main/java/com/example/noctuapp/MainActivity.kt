package com.example.noctuapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.chatbot.FloatingChatbotDialog
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.elements.FloatingActionButton
import com.example.noctuapp.navigation.NavigationApp
import com.example.noctuapp.ui.theme.NoctuappTheme

class MainActivity : ComponentActivity() {

    data class BottomNavigationItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val route: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Noctuapp)
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {
            NoctuappTheme {
                val navController = rememberNavController()
                var showDialog by remember { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute in listOf(
                                selectNavegation.Lugares.route,
                                selectNavegation.Ofertas.route,
                                selectNavegation.Perfil.route,
                                selectNavegation.Chat.route
                            )
                        ) {
                            BottomAppBar().BottomBar(navController, seleccionado = 0)
                        }
                    },
                    floatingActionButton = {
                        if (currentRoute in listOf(
                                selectNavegation.Lugares.route,
                                selectNavegation.Ofertas.route

                            )
                        ) {
                            FloatingActionButton(onClick = {
                                showDialog = true
                            })
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    content = { paddingValues ->
                        if (showDialog) {
                            FloatingChatbotDialog(onDismiss = { showDialog = false })
                        }
                        NavigationApp(
                            ruta = selectNavegation.Login.route,
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                )
            }
        }
    }
}
