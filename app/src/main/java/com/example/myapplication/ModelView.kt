package com.example.myapplication

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ModelView(private val soundManager: SoundManager): ViewModel() {

    private val TAG_LOG = "miDebug"

    //Variable que almacena el estado del juego como observable.
    val estadoLiveData : MutableLiveData<Estados> = MutableLiveData(Estados.INICIO)

    //Lista de colores con mutableList para agregar y eliminar elementos
    private val secuenciaColores = mutableListOf<ColoresBotones>()

    //Variable que guarda el mensaje mostrado por pantalla
    var mensajeC = mutableStateOf("")

    //Variable que almacena el índice de la secuencia de colores
    private var indiceActual = 0

    //Variable de la cuenta atras
    val cuentaAtrasLiveData : MutableLiveData<EstadosCuentaAtras?> = MutableLiveData(EstadosCuentaAtras.AUX0)

    /**
     * Función que inicia el juego.
     * Cambia el estado a GENERANDO y llama a la función crearRandomBoton.
     */

    fun playSound(color: ColoresBotones) {
        soundManager.playSound(color)
    }

    fun empezarPartida() {
        estadoLiveData.value = Estados.GENERANDO
        secuenciaColores.clear()
        generarSecuencia()
     //   cuentaAtras()
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
        mostrarSecuencia()
    }

    /**
     * Función que ilumina el color de la secuencia
     */
    private suspend fun iluminarColor(color: ColoresBotones) {
        mensajeC.value = color.label
        playSound(color)
        delay(1000)
        mensajeC.value = ""
        delay(1000)
    }
    /**
     * Función que muestra la secuencia de colores generada aleatoriamente con el metodo generarSecuencia a traves de secuenciaColores.
     */
    private fun mostrarSecuencia() {
        viewModelScope.launch {
            for (color in secuenciaColores) {
                iluminarColor(color)
            }
            iniciarTurnoJugador()
        }
    }

    /**
     * Función que inicia el turno del jugador cambiando el estado de la aplicación
     */
    private fun iniciarTurnoJugador() {
        estadoLiveData.value = Estados.JUGANDO
        indiceActual = 0
        cuentaAtras() // Iniciar la cuenta atrás para el turno del jugador
    }

    /**
     * Función que compara el color seleccionado por el jugador con metodo GenerarSecuencia, si no es correcto, termina la partida.
     */
    fun compararColorSeleccionado(colorSeleccionado: ColoresBotones): Boolean {
        if (colorSeleccionado == secuenciaColores[indiceActual]) {
            indiceActual++
            if (indiceActual == secuenciaColores.size) {
                estadoLiveData.value = Estados.GENERANDO
                cuentaAtrasLiveData.value = EstadosCuentaAtras.AUX0

                viewModelScope.launch {
                    delay(1500)
                    generarSecuencia()
                }
            }
            return true
        } else {
            terminarPartida()
            return false
        }
    }

    /**
     * Función que finaliza el juego.
     * El estado pasa a perdido e indica por pantalla que la partida ha acabado.
     */
    fun terminarPartida() {
        estadoLiveData.value = Estados.PERDIDO
        mensajeC.value = "Perdiste"
        //Actualización del record alcanzado
        Datos.ronda.value?.let { rondaActual ->
            if(rondaActual > (Datos.recordMaximo.value ?:0)) {
                Datos.recordMaximo.value = rondaActual
            }

        }

        Datos.ronda.value = 0
        Log.d(TAG_LOG, "Estado: ${estadoLiveData.value}")
    }



    /**
     * Función que retorna una lista de botones.
     */
    fun getButtons(): List<ButtonData> {
        return listOf(
            ButtonData(ColoresBotones.VERDE, RoundedCornerShape(topStart = 180.dp)),
            ButtonData(ColoresBotones.ROJO, RoundedCornerShape(topEnd = 180.dp)),
            ButtonData(ColoresBotones.AMARILLO, RoundedCornerShape(bottomStart = 180.dp)),
            ButtonData(ColoresBotones.AZUL, RoundedCornerShape(bottomEnd = 180.dp))
        )
    }

    /**
     * Cuenta atras para seleccionar la secuencia en las rondas, si se acaba el tiempo, la aplicación pasa al estado PERDIDO
     */
    fun cuentaAtras() {
        viewModelScope.launch {
            for (i in 5 downTo 0) {
                // Si el estado del juego ya no es JUGANDO, salimos del contador
                if (estadoLiveData.value != Estados.JUGANDO) {
                    break
                }

                // Actualizar el estado de la cuenta atrás
                cuentaAtrasLiveData.postValue(EstadosCuentaAtras.values().find { it.segundos == i })
                delay(1000) // Esperar 1 segundo
            }

            // Si al finalizar el contador el estado sigue siendo JUGANDO, el jugador pierde
            if (estadoLiveData.value == Estados.JUGANDO) {
                estadoLiveData.postValue(Estados.PERDIDO)
            }
        }
    }

    class SoundManager(context: Context){
        private val soundPool: SoundPool
        private  val soundMap: MutableMap<ColoresBotones, Int> = mutableMapOf()

        init {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build()

            ColoresBotones.values().forEach { color ->
                soundMap[color] = soundPool.load(context, color.soundRes, 1)
            }
        }

            fun playSound(color:ColoresBotones){
                soundMap[color]?.let { soundId ->
                    soundPool.play(soundId, 1f,1f,1,0,1f)

                }
            }

            fun release() {
                soundPool.release()
            }

        }
    }



