package com.example.noctuapp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.OutputStream

// Define una clase para el objeto Empresa
data class Empresa(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val tags: String,
    val ofertas: Int
     // Nueva propiedad añadida
)

// Función para llamar al script PHP y obtener las empresas
fun getEmpresasFromPHP(): Array<Empresa> {
    val url = URL("http://192.168.1.148/get_empresas.php") // Cambia por la ruta correcta
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()

        var inputLine: String?
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        bufferedReader.close()

        // Convierte el JSON de respuesta en un array de objetos Empresa
        return Gson().fromJson(response.toString(), Array<Empresa>::class.java)
    } else {
        throw Exception("Error al obtener los datos de las empresas: $responseCode")
    }
}

fun getEmpresasByTagsFromPHP(tags: String): Array<Empresa> {
    val url = URL("http://192.168.1.148/get_empresas_where_tags.php?tags=$tags") // Cambia por la ruta correcta
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()

        var inputLine: String?
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        bufferedReader.close()

        // Convierte el JSON de respuesta en un array de objetos Empresa
        return Gson().fromJson(response.toString(), Array<Empresa>::class.java)
    } else {
        throw Exception("Error al obtener los datos de las empresas por tags: $responseCode")
    }
}

fun getEmpresasByNombresFromPHP(nombres: Array<String>): Array<Empresa> {
    val url = URL("http://192.168.1.10/get_empresas_by_nombres.php") // Cambia por la ruta correcta
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

    // Crear los datos POST
    val nombresJson = Gson().toJson(nombres)
    val postData = "nombres=$nombresJson"

    // Enviar los datos POST
    val outputStream: OutputStream = connection.outputStream
    outputStream.write(postData.toByteArray())
    outputStream.flush()
    outputStream.close()

    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()

        var inputLine: String?
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        bufferedReader.close()

        // Convierte el JSON de respuesta en un array de objetos Empresa
        val listType = object : TypeToken<Array<Empresa>>() {}.type
        return Gson().fromJson(response.toString(), listType)
    } else {
        throw Exception("Error al obtener los datos de las empresas: $responseCode")
    }
}

fun main() {
    try {
        val empresas = getEmpresasFromPHP()
        // Imprime las empresas obtenidas
        empresas.forEach { println(it) }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
