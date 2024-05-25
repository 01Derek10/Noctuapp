package com.example.noctuapp.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noctuapp.R

sealed class Items_menu(
    val icon: ImageVector,
    val title: String,
    val route: String
    ){
    object Lugares : Items_menu(Icons.Filled.Place, "Lugares", "Lugares")
    object Perfil : Items_menu(Icons.Filled.Person, "Perfil", "Perfil")
    object Ofertas : Items_menu(Icons.Filled.LocalOffer, "Ofertas", "Ofertas")

}