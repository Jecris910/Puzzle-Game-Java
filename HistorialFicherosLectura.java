/*
CLASE HistorialFicherosLectura permite la lectura línea a línea del historial
de partidas desde un fichero de texto utilizando la clase auxiliar LineaFicherosLectura.
*/
package practicafinalpuzzle;

public class HistorialFicherosLectura {
    // DECLARACIÓN DEL ATRIBUTO
    // objeto LineaFicherosLectura que gestiona la lectura del fichero línea a línea
    private LineaFicherosLectura fichero = null;

    // MÉTODO CONSTRUCTOR
    public HistorialFicherosLectura(String nombreFichero) throws Exception {
        // instanciación del objeto LineaFicherosLectura con el nombre del fichero proporcionado
        fichero = new LineaFicherosLectura(nombreFichero);
    }

    // MÉTODO FUNCIONAL
    // método leerHistorialCompleto lee todas las líneas del fichero y las concatena en un String
    public String leerHistorialCompleto() throws Exception {
        String contenido = ""; // acumulador del contenido del fichero
        // bucle de lectura mientras queden líneas en el fichero
        while (fichero.quedanLineas()) {
            Linea l = fichero.lectura(); // lectura de una línea
            contenido += l.toString() + "\n"; // conversión a texto y concatenación con salto de línea
        }
        return contenido; // devolución del contenido completo
    }

    // método cerrar cierra el acceso al fichero
    public void cerrar() throws Exception {
        fichero.cierre(); // invoca el método cierre del objeto LineaFicherosLectura
    }
}
