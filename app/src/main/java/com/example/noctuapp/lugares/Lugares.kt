package com.example.noctuapp.lugares


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noctuapp.ui.theme.NoctuappTheme



@SuppressLint("RestrictedApi")
@Composable
fun VistaLugares(navController: NavController) {

}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaLoginPreview() {
    NoctuappTheme {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }

        }

    }
