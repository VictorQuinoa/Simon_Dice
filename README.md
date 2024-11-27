# Simon_Dice



<p align="center">
  <img src="https://github.com/VictorQuinoa/Simon_Dice/blob/main/Imagenes/Simon.png?raw=true" />
</p>

## Desarrollo de la aplicación simón dice

Para la creación de esta aplicación fueron necesarias 3 clases:


- Datos , encargada de la información de los botones y los estados de la aplicación.

- ModelView, encargada de la lógica del programa.

- IU, encargada de la interacción con el usuario y el empleo de la ModelView


### Datos

En esta clase se guarda la información de los botones ( color y número asignado), junto a los estados del programa (Inicio, Generando, Jugando y Perdido)

Los estados se encargan de mantener un orden, por ejemplo, cuando el estado es Inicio, lo único activo en el programa es el boton de inicio, una vez pulsado se pasa al estado generando, que genera la secuencia y desactiva el boton de inicio, y al terminar empieza la interacción con el usuario con el estado Adivinando.


### ModelView

Aqui se establecen los metodos que daran funcionalidad al juego, estos metodos son:

- *empezarPartida*: Inicia el juego llamando a la generación de la secuencia, cambiando el estado a generando.

- *generarSecuencia*: Genera una secuencia aleatoria teniendo en cuenta la clase Datos para asignar cada numero generado al botón de su color.

- *mostrarSecuencia*: Se encarga de mandar el mensaje que indica el botón que debe pulsar el jugador.

- *compararColorSeleccionado*: Este metodo comprueba si el botón pulsado por el jugador coincide con el generado por el programa, si es el mismo, se pasa la ronda, en caso contrario, el estado pasa a Perdido y vuelve a Inicio.

- *terminarPartida*: En caso de que el estado pase a Perdido, lanza el mensaje de derrota.

- *getButtons*: Devuelve la lista con los botones del juego

### IU

Este es el apartado visual e interactivo del programa, aqui es donde se muestran los mensajes sobre la ronda o la derrota. Tambien es donde el usuario puede interacturar con los botones para iniciar la partida, o para introducir la secuencia.


### Observer

El observer es utilizado para este proyecto para mantener la sincronizacion del juego, sus funciones son:

- El control de los estados, manteniendo actualizados los cambios para que el programa se ejecute correctamente.
- Los cambios en las rondas

  ```
  val estado by viewModel.estadoLiveData.observeAsState(Estados.INICIO)

  val ronda by Datos.ronda.observeAsState(0)
  ```

  ### Corutinas

 La corutinas se emplean para evitar interupciones en la ejecución en determinados momentos:
 
 - Para mostrar las secuencias de colores a repetir.
   
   ```
   private fun mostrarSecuencia() {
    viewModelScope.launch {  // Lanza una corrutina en el ViewModel
        for (color in secuenciaColores) {
            mensajeC.value = color.label  // Actualiza el mensaje del color actual
            delay(500)  // Muestra el color durante 500 ms
            mensajeC.value = ""  // Limpia el mensaje
            delay(500)  // Pausa antes del siguiente color
        }
        estadoLiveData.value = Estados.JUGANDO  // Cambia el estado al finalizar la secuencia
        indiceActual = 0
    } 
   }
   ```
 - Generar el nuevo color de la próxima ronda
   
   ```
   if (indiceActual == secuenciaColores.size) {
    estadoLiveData.value = Estados.GENERANDO
    viewModelScope.launch {
        delay(1500)  // Pausa antes de generar el siguiente color
        generarSecuencia()
    }
   }
   ```
   
 - El cambio de color en el botón que indica que forma parte de la secuencia

   ```
   if (iluminado == buttonData.colorButton) {
    LaunchedEffect(iluminado) {
        delay(500)  // Ilumina el botón durante 500 ms
        iluminado = null  // Apaga la iluminación
    }
   }
   ```

  ### Imagen de la aplicación 
  
  <p align="center">
    <img src="https://github.com/VictorQuinoa/Simon_Dice/blob/main/Imagenes/Aplicacion.png?raw=true" />
  </p>


