package com.example.noctuapp.elements

import com.example.noctuapp.R

sealed class Items_menu (
    val icon : Int,
    val title: String,
    val route: String
    ){
    object Lugares : Items_menu(R.drawable.lugares_logo, "Lugares", "Lugares")
    object Perfil : Items_menu(R.drawable.perfil_logo, "Perfil", "Perfil")
    object Ofertas : Items_menu(R.drawable.oferta_logo, "Ofertas", "Ofertas")

}