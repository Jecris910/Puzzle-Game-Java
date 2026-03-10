/*
CLASE Main contiene el método main que actúa como punto de entrada del programa.
Desde aquí se inicia una nueva partida del puzzle llamando al método inicializar().

AUTOR: Cristian Morales García
*/
package practicafinalpuzzle;

public class Main {
    public static void main(String[] args) {
        // creación de un nuevo objeto Partida e inicialización del juego
        new Partida().inicializar();
    }
}
