 package com.example.noctuapp.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noctuapp.login.VistaLogin
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.lugares.VistaLugares
import com.example.noctuapp.ofertas.VistaOfertas
import com.example.noctuapp.perfil.VistaPerfil


 @Composable
fun NavigationApp(ruta: String, navController: NavHostController, modifier: Modifier){
    NavHost(navController = navController, startDestination = ruta ) {
        composable(route = selectNavegation.Login.route) {
            VistaLogin(navController = navController)
        }

        composable(route = selectNavegation.Lugares.route) {
            VistaLugares(navController = navController, bottomAppBar = BottomAppBar())
        }

        composable(route = selectNavegation.Perfil.route) {
            VistaPerfil(navController = navController, bottomAppBar = BottomAppBar())
        }

        composable(route = selectNavegation.Ofertas.route) {
            VistaOfertas(navController = navController, bottomAppBar = BottomAppBar())
        }
    }

}