package com.example.noctuapp.clientchat

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.myapplication.navegation.selectNavegation
import com.example.noctuapp.Empresa
import com.example.noctuapp.getEmpresasFromPHP
import com.example.noctuapp.ui.theme.noctuapp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NoctuBot {

    private val greetingKeywords = listOf("hola","buenas","buenos dias", "buenas tardes")
    private val byeKeywords = listOf("adios","hasta luego","gracias",)

    fun getResponse(message: String, empresas: Array<Empresa>): String {
        val nombreEmpresas = mutableListOf("asfdasdfadsfghsahgsfdjkgshjbfgbadshjf")
        val tagsEmpresa = mutableListOf("asfdasdfadsfghsahgsfdjkgshjbfgbadshjf")
        empresas.forEach {elemento ->
            nombreEmpresas.add(elemento.nombre)
        }
        empresas.forEach { elemento ->
            val tags = elemento.tags.split(",")
            tags.forEach{tag ->
                if(!tagsEmpresa.contains(tag)){
                    tagsEmpresa.add(tag)
                }
            }
        }
        Log.i("MENSAJE", message)
        return if (nombreEmpresas.any{ message.contains(it, ignoreCase = true)} || tagsEmpresa.any{ message.contains(it, ignoreCase = true)} || greetingKeywords.any{ message.contains(it, ignoreCase = true) } || byeKeywords.any{ message.contains(it, ignoreCase = true) }) {
            generateMessage(nombreEmpresas = nombreEmpresas, message = message, empresas = empresas, tagsEmpresa = tagsEmpresa, greetingKeywords, byeKeywords)
        } else {
            "Preguntame sobre locales, no puedo ayudarte con otras cosas :("
        }
    }

    fun generateMessage(nombreEmpresas: MutableList<String>, message: String, empresas: Array<Empresa>, tagsEmpresa: MutableList<String>, greetingKeywords: List<String>, byeKeywords: List<String>): String {
        var addedTitle = false
        var addedGreeting = false
        var addedBye = false
        var result = String()
        Log.i("MENSAJE", message)
        greetingKeywords.forEach { word ->
            if(message.contains(word, ignoreCase = true) && !addedGreeting){
                result += "Buenas!, estoy aqui para ayudarte, comentame ¿qué necesitas?"
                addedGreeting = true
            }
        }
        
        nombreEmpresas.forEach { empresa ->
            Log.i("EMPRESA", empresa)
            if(message.contains(empresa, ignoreCase = true)){
                Log.i("EMPRESA", empresa)
                Log.i("INDEX", nombreEmpresas.indexOf(empresa).toString())
                var index = nombreEmpresas.indexOf(empresa)
                index -= 1
                val item = empresas.get(index)
                if(addedGreeting){
                    result += "\n\n"
                }
                if(!result.contains(empresa, ignoreCase = true)){
                    result += "NOMBRE: ${item.nombre} \nUBICACIÓN: ${item.ubicacion}"
                }
            }
        }

        if(result.isNotEmpty()){
            result += "\n"
        }

        tagsEmpresa.forEach { tag ->
            Log.i("TAG", tag)
            if(message.contains(tag, ignoreCase = true) && !result.contains(tag)){
                if(!addedTitle){
                    result += "Las empresas que coinciden con dichas características son:\n"
                    addedTitle=true
                }
                Log.i("TAG", tag)
                Log.i("INDEX", tagsEmpresa.indexOf(tag).toString())
                empresas.forEach { empresa ->
                    if(empresa.tags.contains(tag,ignoreCase = true) && !result.contains(empresa.nombre,ignoreCase = true)){
                        result += "\n${empresa.nombre}"
                    }
                }
            }
        }

        byeKeywords.forEach { word ->
            if(message.contains(word, ignoreCase = true) && !addedBye && result.isEmpty()){
                result += "Ha sido un placer ayudarte."
                addedBye = true
            } else if(message.contains(word, ignoreCase = true) && !addedBye && !result.isEmpty()){
                result += "\n\nHa sido un placer ayudarte."
                addedBye = true
            }
        }
        if(result.isEmpty()){
            result = "Preguntame sobre locales, no puedo ayudarte con otras cosas :("
        }
        addedTitle = false
        addedGreeting = false
        addedBye = false
        return result
    }

}
