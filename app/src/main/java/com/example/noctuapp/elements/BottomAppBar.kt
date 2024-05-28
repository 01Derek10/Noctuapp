package com.example.noctuapp.elements

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.MainActivity

class BottomAppBar {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun BottomBar(navController: NavController, seleccionado: Int) {
        val items = listOf(
            MainActivity.BottomNavigationItem(
                title = "Lugares",
                selectedIcon = Icons.Filled.LocationOn,
                unselectedIcon = Icons.Outlined.LocationOn,
                route = selectNavegation.Lugares.route
            ),
            MainActivity.BottomNavigationItem(
                title = "Ofertas",
                selectedIcon = Icons.Filled.LocalOffer,
                unselectedIcon = Icons.Outlined.LocalOffer,
                route = selectNavegation.Ofertas.route
            ),
            MainActivity.BottomNavigationItem(
                title = "Chat",
                selectedIcon = Icons.Filled.LocalOffer,
                unselectedIcon = Icons.Outlined.LocalOffer,
                route = selectNavegation.Chat.route
            ),
            MainActivity.BottomNavigationItem(
                title = "Perfil",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                route = selectNavegation.Perfil.route
            )
        )
        var selectedItemIndexed by remember { mutableStateOf(seleccionado) }

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndexed == index,
                    onClick = {
                        selectedItemIndexed = index
                        navController.navigate(item.route)
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        BadgedBox(badge = { }) {
                            Icon(
                                imageVector = if (selectedItemIndexed == index) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        }
    }
}
