package com.example.noctuapp.lugares


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.BottomAppBar
import com.example.noctuapp.BottomAppBar.*
import com.example.noctuapp.Items_menu
import com.example.noctuapp.Items_menu.*
import com.example.noctuapp.R
import com.example.noctuapp.ui.theme.NoctuappTheme



@SuppressLint("RestrictedApi")
@Composable
fun VistaLugares(navController: NavController, bottomAppBar:BottomAppBar) {
    bottomAppBar.bottomBar()



    NoctuappTheme {

        val listPlaces = listOf(
            PartyPlace(1,"Party Place 1", "Location 1"),
            PartyPlace(2,"Party Place 2", "Location 2"),
            PartyPlace(3,"Party Place 3", "Location 3"),
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
                    Text(text = place.name, style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Age: ${place.location}", style = MaterialTheme.typography.bodyMedium)
                }
            }

        }
        LazyColumn {
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
fun VistaLoginPreview( ) {
    NoctuappTheme {
        VistaLugares(rememberNavController(), BottomAppBar())
    }


        }