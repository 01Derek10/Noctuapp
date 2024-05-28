package com.example.noctuapp.clientchat

import com.example.noctuapp.getEmpresasFromPHP

object NoctuBot {

    private val pubKeywords = listOf("pub", "bar", "ale", "beer", "brewery")

    val empresas = getEmpresasFromPHP()
    fun getResponse(message: String): String {

        return if (pubKeywords.any { message.contains(it, ignoreCase = true) }) {
            "¿Quieres encontrar tu sitio ideal? Has venido al sitio correcto."
        } else {
            "Preguntame sobre locales, no puedo ayudarte con otras cosas :("
        }
    }
}