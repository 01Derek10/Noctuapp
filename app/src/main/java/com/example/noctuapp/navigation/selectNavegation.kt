package com.example.myapplication.navegation

sealed class selectNavegation(val route:String) {

    object Login:selectNavegation("login")

    object Lugares:selectNavegation("lugares")

}



