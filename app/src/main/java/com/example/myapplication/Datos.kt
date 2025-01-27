package com.example.myapplication

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData


object Datos {
    /**
     * Variables numero, ronda y record máximo alcanzado.
     */
    var numero: Int = 0
    var ronda : MutableLiveData <Int> = MutableLiveData(0)
    var recordMaximo: MutableLiveData<Int> = MutableLiveData(0)

}


/**
 * Clase que contiene los colores de cada botón y su valor asignado.
 */
enum class ColoresBotones(val color: Color, val label: String, val value: Int,val soundRes: Int) {
    VERDE(Color.Green, "Verde", 1, R.raw.sonido_boton),
    ROJO(Color.Red, "Rojo", 2,R.raw.sonido_boton),
    AMARILLO(Color.Yellow, "Amarillo", 3, R.raw.sonido_boton),
    AZUL(Color.Blue, "Azul", 4, R.raw.sonido_boton)
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

/**
 * Estados auxilares utilizados para el contador de la aplicación
 * @param segundos: numer de la cuenta atrás
 */
enum class EstadosCuentaAtras(val segundos: Int){
    AUX0(0),
    AUX1(1),
    AUX2(2),
    AUX3(3),
    AUX4(4),
    AUX5(5)
}
