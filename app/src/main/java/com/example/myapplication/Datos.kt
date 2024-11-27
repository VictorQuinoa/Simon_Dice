package com.example.myapplication

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData


object Datos {
    /**
     * Variables numero y ronda.
     */
    var numero: Int = 0
    var ronda : MutableLiveData <Int> = MutableLiveData(0)
}


/**
 * Clase que contiene los colores de cada botón y su valor asignado.
 */
enum class ColoresBotones(val color: Color, val label: String, val value: Int) {
    VERDE(Color.Green, "Verde", 1),
    ROJO(Color.Red, "Rojo", 2),
    AMARILLO(Color.Yellow, "Amarillo", 3),
    AZUL(Color.Blue, "Azul", 4)
}


/**
 * Clase que se encarga de almacenar los datos de los botones y establece la forma redondeada de sus esquinas.
 */
data class ButtonData(val colorButton: ColoresBotones, val shape: RoundedCornerShape)


/**
 * Estados del juego
 */
enum class Estados (val value: Int, val label: String) {
    INICIO(0, "Inicio"),
    GENERANDO(1, "Generando"),
    JUGANDO(2, "Adivinando"),
    PERDIDO(3, "Perdido")


}
