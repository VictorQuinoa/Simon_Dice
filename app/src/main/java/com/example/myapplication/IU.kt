package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay


/**
 * Función que se encarga de mostrar la interfaz de usuario.
 */
@Composable
fun IU(viewModel: ModelView) {
    val TAG_LOG = "miDebug"
    val estado by viewModel.estadoLiveData.observeAsState(Estados.INICIO)
    val ronda by Datos.ronda.observeAsState(0) // Observa el LiveData de ronda


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.White)
    ) {
        if (estado == Estados.PERDIDO) {
            Text(
                text = "Has perdido",
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }


        Text(
            text = "Ronda: $ronda",
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )


        Botones(viewModel, estado, TAG_LOG)
        Boton_Start(viewModel, estado)
    }
}


/**
 * Función que se encarga de mostrar los botones de colores de la aplicación.
 */
@Composable
fun Botones(viewModel: ModelView, estado: Estados, TAG_LOG: String) {
    val buttons = viewModel.getButtons()
    val mensajeC by viewModel.mensajeC
    var iluminado by remember { mutableStateOf<ColoresBotones?>(null) }


    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp) // Espaciado interno
    ) {
        buttons.chunked(2).forEach { rowButtons ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.Gray) // Fondo gris para cada fila
                    .clip(RoundedCornerShape(16.dp))
                    .padding(8.dp) // Espaciado interno para separar botones
            ) {
                rowButtons.forEach { buttonData ->
                    val isIlluminated = mensajeC == buttonData.colorButton.label || iluminado == buttonData.colorButton
                    Button(
                        onClick = {
                            if (estado == Estados.JUGANDO) {
                                Log.d(TAG_LOG, buttonData.colorButton.label)
                                iluminado = buttonData.colorButton
                                val isCorrect = viewModel.compararColorSeleccionado(buttonData.colorButton)
                                if (!isCorrect) {
                                    viewModel.terminarPartida()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isIlluminated) buttonData.colorButton.color.copy(alpha = 0.5f) else buttonData.colorButton.color
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                            .size(width = 180.dp, height = 180.dp),
                        shape = RoundedCornerShape(16.dp), // Bordes redondeados para el botón
                    ) {}


                    if (iluminado == buttonData.colorButton) {
                        LaunchedEffect(iluminado) {
                            delay(500)
                            iluminado = null
                        }
                    }
                }
            }
        }
    }
}
/**
 * Función que se encarga de mostrar el botón para iniciar la partida.
 */
@Composable
fun Boton_Start(viewModel: ModelView, estado: Estados) {
    Button(
        onClick = {
            viewModel.empezarPartida()
        },
        modifier = Modifier
            .padding(5.dp)
            .size(width = 180.dp, height = 50.dp),
        enabled = estado == Estados.INICIO || estado == Estados.PERDIDO
    ) {
        Text("Start")
    }
}
