package com.example.noctuapp.lugares

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.ui.theme.NoctuappTheme
import com.example.noctuapp.ui.theme.noctuapp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.io.encoding.Base64
import kotlin.math.exp

// Modelo de datos
data class PartyPlace(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val map: String,
    val tags: String,
    val descripcion: String,
    val imagen: ByteArray
)

// Repositorio para gestionar los datos
class LugaresRepository {
    suspend fun getLugares(): List<PartyPlace> {
        val url = URL("http://192.168.1.60/get_empresas.php")
        val connection = withContext(Dispatchers.IO) { url.openConnection() as HttpURLConnection }
        connection.requestMethod = "GET"

        return withContext(Dispatchers.IO) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            Log.d("LugaresRepository", "Response: $response")
            val jsonArray = JSONArray(response)

            val places = mutableListOf<PartyPlace>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val imageString = jsonObject.optString("imagen",null)
                val place = PartyPlace(
                    id = jsonObject.getInt("id"),
                    nombre = jsonObject.getString("nombre"),
                    ubicacion = jsonObject.getString("ubicacion"),
                    map = jsonObject.getString("map"),
                    tags = jsonObject.getString("tags"),
                    descripcion = jsonObject.getString("descripcion"),
                    imagen =
                    java.util.Base64.getDecoder().decode(imageString)

                )
                places.add(place)
            }
            places
        }
    }
    suspend fun getLugaresByMusic(music: String): List<PartyPlace> {
        val allPlaces = getLugares()
        return allPlaces.filter { place ->
            place.tags.contains(music, ignoreCase = true)
        }
    }
}

@Composable
fun VistaLugares(navController: NavController, bottomAppBar: BottomAppBar, lugaresRepository: LugaresRepository) {
    val places = remember { mutableStateOf<List<PartyPlace>>(emptyList()) }

    // Cargar los datos al iniciar la vista
    LaunchedEffect(Unit) {
        val data = lugaresRepository.getLugares()
        places.value = data
    }

    NoctuappTheme {
        LazyColumn(modifier = Modifier.padding(bottom = 80.dp)) {
            items(items = places.value, itemContent = { place ->
                PlaceItem(place = place)
            })
        }
    }
}
@Composable
fun LugaresFilteredByMusic(
    lugaresRepository: LugaresRepository,
    selectedMusic: String?,
    modifier: Modifier = Modifier
) {
    val placesM = remember { mutableStateOf<List<PartyPlace>>(emptyList()) }

    LaunchedEffect(selectedMusic) {
        if (selectedMusic != null) {
            val filteredPlaces = lugaresRepository.getLugaresByMusic(selectedMusic)
            placesM.value = filteredPlaces
        }
    }

    LazyColumn(modifier = modifier) {
        items(items = placesM.value) { place ->
            PlaceItem(place = place)
        }
    }
}


@Composable
fun PlaceItem(place: PartyPlace) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = noctuapp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row{
            val bitmap = place.imagen?.let { BitmapFactory.decodeByteArray(place.imagen, 0, it.size) }
            if(!expanded){
                if (bitmap != null) {
                    Image(painter = BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription =null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(84.dp)
                            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterVertically))
            {
                Spacer(modifier = Modifier.size(10.dp))
                if (expanded) {
                    if (bitmap != null) {
                        Image(painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription =null,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(200.dp)
                                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }

                Text(text = place.nombre, style = MaterialTheme.typography.headlineLarge)
                Text(
                    text = "Ubicación: ${place.ubicacion}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (expanded) {
                    Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "Estilo: ${place.tags}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Descripción: ${place.descripcion}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(place.map))
                        context.startActivity(intent)
                    }) {
                        Row {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                        }
                    }
                }
            }
        }
    }



}