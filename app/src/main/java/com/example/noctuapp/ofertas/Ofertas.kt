package com.example.noctuapp.ofertas

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.Empresa
import com.example.noctuapp.elements.BottomAppBar
import com.example.noctuapp.ui.theme.NoctuappTheme
import com.example.noctuapp.ui.theme.noctuapp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

data class Ofertas(
    val nombreEmpresa: String,
    val descripcion: String,
    val enlace: String
)

class OfertaRepository {
    suspend fun getofertas(): List<Ofertas> {
        val url = URL("http://192.168.251.190/ofertas.php")
        val connection = withContext(Dispatchers.IO) { url.openConnection() as HttpURLConnection }
        connection.requestMethod = "GET"

        return withContext(Dispatchers.IO) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)
            val lista = mutableListOf<Ofertas>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val place = Ofertas(
                    nombreEmpresa = jsonObject.getString("nombre"),
                    descripcion = jsonObject.getString("descripcion"),
                    enlace = jsonObject.getString("enlace"),
                )
                lista.add(place)
            }
            lista
        }
    }
}
@Composable
fun VistaOfertas(navController: NavController, bottomAppBar: BottomAppBar, ofertaRepository: OfertaRepository) {
    val  lista = remember {
        mutableStateOf<List<Ofertas>>(emptyList())
    }
    LaunchedEffect(Unit) {
        val data = ofertaRepository.getofertas()
        lista.value=data;
    }
    NoctuappTheme {


        @Composable
        fun PlaceItem(oferta: Ofertas) {
            val context = LocalContext.current
            var expanded by remember { mutableStateOf(false) }
            Card (
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },

                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = noctuapp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = oferta.nombreEmpresa, style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Información: ${oferta.descripcion}", style = MaterialTheme.typography.bodyMedium)
                    if(expanded){
                        IconButton(onClick = {

                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(oferta.enlace)
                                )


                            context.startActivity(intent)
                        }) {
                            Row {
                                Icon(imageVector = Icons.Filled.Public, contentDescription = null)
                            }
                        }
                    }
                }
            }

        }
        LazyColumn(
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            items(
                items = lista.value,
                itemContent = { oferta ->
                    PlaceItem(oferta = oferta)
                }
            )
        }

    }

}



