package com.example.noctuapp.ofertas

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.ui.theme.NoctuappTheme

@Composable
fun VistaOfertas(navController: NavController, bottomAppBar: BottomAppBar) {
    bottomAppBar.BottomBar(navController,1)
    NoctuappTheme {

        val listPlaces = listOf(
            Oferta(1, "Sala Gold", "2x1 en consumiciones hasta las 2:00"),
            Oferta(2, "Theatro Club", "Calle Lazcano, 5"),
            Oferta(3, "Andén", "Plaza de Uncibay, 8"),
            Oferta(4, "Discoteca Liceo", "Calle Beatas, 21"),
            Oferta(5, "Sala Wenge", "Calle Santa Lucía, 11"),
            Oferta(6, "Velvet Club", "Calle Convalecientes, 11"),
            Oferta(7, "Bubbles Lounge Club", "Calle Juan de Padilla, 18"),
            Oferta(8, "Antigua Casa de Guardia", "Alameda Principal, 18"),
            Oferta(9, "ZZ Pub", "Calle Tejón y Rodríguez, 6"),
            Oferta(10, "La Botellita", "Calle Luis de Velázquez, 3"),
            Oferta(11, "Clarence Jazz Club", "Calle Cañón, 5"),
            Oferta(12, "Sala Premier", "Calle Molina Lario, 2"),
            Oferta(13, "Malafama", "Calle Comedias, 15"),
            Oferta(14, "Sala White", "Calle José Denis Belgrano, 3"),
            Oferta(15, "The Hall", "Calle Héroe de Sostoa, 65"),
        )
        @Composable
        fun PlaceItem(place: Oferta) {
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
                    Text(text = place.name, style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Información: ${place.informacion}", style = MaterialTheme.typography.bodyMedium)
                }
            }

        }
        LazyColumn(
            modifier = Modifier.padding(bottom = 80.dp)
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

data class Oferta(
    val id: Int,
    val name: String,
    val informacion: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaOfertasPreview( ) {
    NoctuappTheme {
        VistaOfertas(rememberNavController(), BottomAppBar())
    }
}
