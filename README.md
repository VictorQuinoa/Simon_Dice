# Simon_Dice

## Desarrollo de la aplicación simón dice

Para la creación de esta aplicación fueron necesarias 3 clases:


- Datos , encargada de la información de los botones y los estados de la aplicación.

- ModelView, encargada de la lógica del programa.

- IU, encargada de la interacción con el usuario y el empleo de la ModelView


### Datos

En esta clase se guarda la información de los botones ( color y número asignado), junto a los estados del programa (Inicio, Generando, Adivinando y Perdido)

Los estados se encargan de mantener un orden, por ejemplo, cuando el estado es Inicio, lo único activo en el programa es el boton de inicio, una vez pulsado se pasa al estado generando, que genera la secuencia, y al terminar empieza la interaccion con el usuario con el estado Adivinando.




