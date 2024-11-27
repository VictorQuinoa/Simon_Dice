package com.example.myapplication

import androidx.lifecycle.MutableLiveData

class ModelView {

    //Variable que almacena el estado del juego como observable.
    val estadoLiveData : MutableLiveData<Estados> = MutableLiveData(Estados.INICIO)

    //Lista de colores con mutableList para agregar y eliminar elementos
    private val secuenciaColores = mutableListOf<ColoresBotones>()

    /**
     * Función que inicia el juego.
     * Cambia el estado a GENERANDO y llama a la función crearRandomBoton.
     */
    fun empezarPartida() {
        estadoLiveData.value = Estados.GENERANDO
        secuenciaColores.clear()
        generarSecuencia()
    }

    /**
     * Función que agrega un color a la secuencia de colores, tomando la informacion del color de la clase Datos.
     */
    // In `ModelView.kt`
    fun generarSecuencia() {
        val randomButtonIndex = (1..4).random()
        val ColorSecuencia = ColoresBotones.values().first { it.value == randomButtonIndex }
        secuenciaColores.add(ColorSecuencia)
        Datos.ronda.value = Datos.ronda.value?.plus(1) // Incrementa la ronda

    }


}