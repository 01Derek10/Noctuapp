package com.example.noctuapp.lugares


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.elements.BottomAppBar

import com.example.noctuapp.ui.theme.NoctuappTheme



@SuppressLint("RestrictedApi")
@Composable
fun VistaLugares(navController: NavController, bottomAppBar: BottomAppBar) {
    bottomAppBar.BottomBar(navController,0)
    NoctuappTheme {
        val listPlaces = listOf(
            PartyPlace(1, "Sala Gold", "Calle Luis de Velázquez, 5"),
            PartyPlace(2, "Theatro Club", "Calle Lazcano, 5"),
            PartyPlace(3, "Andén", "Plaza de Uncibay, 8"),
            PartyPlace(4, "Discoteca Liceo", "Calle Beatas, 21"),
            PartyPlace(5, "Sala Wenge", "Calle Santa Lucía, 11"),
            PartyPlace(6, "Velvet Club", "Calle Convalecientes, 11"),
            PartyPlace(7, "Bubbles Lounge Club", "Calle Juan de Padilla, 18"),
            PartyPlace(8, "Antigua Casa de Guardia", "Alameda Principal, 18"),
            PartyPlace(9, "ZZ Pub", "Calle Tejón y Rodríguez, 6"),
            PartyPlace(10, "La Botellita", "Calle Luis de Velázquez, 3"),
            PartyPlace(11, "Clarence Jazz Club", "Calle Cañón, 5"),
            PartyPlace(12, "Sala Premier", "Calle Molina Lario, 2"),
            PartyPlace(13, "Malafama", "Calle Comedias, 15"),
            PartyPlace(14, "Sala White", "Calle José Denis Belgrano, 3"),
            PartyPlace(15, "The Hall", "Calle Héroe de Sostoa, 65"),
        )
        @Composable
        fun PlaceItem(place: PartyPlace) {
            Card (
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = place.name, style = typography.headlineLarge)
                        Text(text = "Ubicación: ${place.location}", style = typography.bodyMedium)
                    }
                }

        }
        LazyColumn(modifier = Modifier.padding(bottom = 80.dp)
        ) {

            items(
                items = listPlaces,
                itemContent = { place ->
                    PlaceItem(place = place)
                }
            )
        }


    }




}

data class PartyPlace(
    val id: Int,
    val name: String,
    val location: String
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaLoginPreview() {
    NoctuappTheme {
        VistaLugares(rememberNavController(), BottomAppBar())
    }
}