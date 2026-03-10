/*
CLASE Casilla representa una casilla del tablero del puzzle.
Almacena su valor y su posición (fila y columna).
También proporciona métodos para acceder y modificar esta información.
*/
package practicafinalpuzzle;

class Casilla {
    // DECLARACIÓN DE LOS ATRIBUTOS
    // valor numérico de la casilla (0 representa la casilla vacía)
    private int valor;
    // fila en la que se encuentra la casilla
    private int fila;
    // columna en la que se encuentra la casilla
    private int columna;

    // MÉTODO CONSTRUCTOR
    public Casilla(int valor, int fila, int columna) {
        // inicialización de los atributos con los valores proporcionados
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    // MÉTODOS FUNCIONALES
    // método getValor devuelve el valor de la casilla
    public int getValor() {
        return valor;
    }

    // método setValor permite modificar el valor de la casilla
    public void setValor(int valor) {
        this.valor = valor;
    }

    // método getFila devuelve la fila en la que se encuentra la casilla
    public int getFila() {
        return fila;
    }

    // método getColumna devuelve la columna en la que se encuentra la casilla
    public int getColumna() {
        return columna;
    }

    // método estaVacia indica si la casilla está vacía (valor == 0)
    public boolean estaVacia() {
        if (valor == 0) {
            return true;
        } else {
            return false;
        }
    }
}
