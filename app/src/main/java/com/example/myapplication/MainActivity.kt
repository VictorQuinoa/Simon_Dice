package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme




/**
 * Clase MainActivity que se encarga de ejecutar el juego mediante la llamada de la IU.
 */
class MainActivity : ComponentActivity() {
    private lateinit var soundManager: ModelView.SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = ModelView.SoundManager(this) // Inicializar SoundManager

        val modelView = ModelView(soundManager) // Pasar SoundManager al ViewModel

        setContent {
            MyApplicationTheme {
                Surface(
                    color = Color.Black,
                    modifier = Modifier.fillMaxSize()
                ) {
                    IU(modelView)
                }
            }
        }
    }
}
