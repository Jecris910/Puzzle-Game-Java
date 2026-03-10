/*
CLASE HistorialFicherosEscritura permite la escritura de líneas de texto en el
fichero del historial de partidas, utilizando la clase auxiliar LineaFicherosEscritura.
Admite modo de adición para conservar contenido anterior.
*/
package practicafinalpuzzle;

public class HistorialFicherosEscritura {
    // DECLARACIÓN DEL ATRIBUTO
    // objeto LineaFicherosEscritura encargado de gestionar la escritura en el fichero
    private LineaFicherosEscritura fichero = null;

    // MÉTODO CONSTRUCTOR
    public HistorialFicherosEscritura(String nombreFichero, boolean adiccion) throws Exception {
        // instanciación del objeto LineaFicherosEscritura con el nombre del fichero y modo de adición
        fichero = new LineaFicherosEscritura(nombreFichero, adiccion);
    }

    // MÉTODO FUNCIONAL
    // método escribirLinea permite escribir una nueva línea en el fichero del historial
    public void escribirLinea(String linea) throws Exception {
        // crea un objeto Linea con el contenido proporcionado y lo escribe en el fichero
        fichero.escritura(new Linea(linea));
    }

    // método cerrar cierra el acceso al fichero
    public void cerrar() throws Exception {
        fichero.cierre(); // invoca el método cierre del objeto LineaFicherosEscritura
    }
}
