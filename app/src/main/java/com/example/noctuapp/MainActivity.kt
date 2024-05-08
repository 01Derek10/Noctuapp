package com.example.noctuapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.navigation.NavigationApp
import com.example.noctuapp.ui.theme.NoctuappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Noctuapp)
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            setContent {
                NoctuappTheme {
                    NavigationApp(ruta=selectNavegation.Login.route)
                }
            }
    }

}