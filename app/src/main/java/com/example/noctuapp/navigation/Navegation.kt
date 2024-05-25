 package com.example.noctuapp.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.login.VistaLogin
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.elements.Items_menu
import com.example.noctuapp.lugares.VistaLugares
import com.example.noctuapp.ofertas.Ofertas
import com.example.noctuapp.perfil.Perfil


 @Composable
fun NavigationApp(ruta:String){
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = ruta ) {
        composable(route = selectNavegation.Login.route) {
            VistaLogin(navController = navController)
        }

        composable(route = selectNavegation.Lugares.route) {
            VistaLugares(navController = navController, bottomAppBar = BottomAppBar())
        }

        composable(route = selectNavegation.Perfil.route) {
            Perfil(navController = navController, bottomAppBar = BottomAppBar())
        }

        composable(route = selectNavegation.Ofertas.route) {
            Ofertas(navController = navController, bottomAppBar = BottomAppBar())
        }
    }

}