package com.example.noctuapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController =navController, startDestination = Items_menu.Lugares.route,){
        composable(Items_menu.Lugares.route){
            Lugares()
        }
        composable(Items_menu.Ofertas.route){
            Ofertas()
        }
        composable(Items_menu.Perfil.route){
            Perfil()
        }
    }
}
