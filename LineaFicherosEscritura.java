/*
Clase LineaFicherosEscritura --> aglutina las declaraciones y funcionalidades que
posibilitan la gestión de la escritura de objetos Linea en ficheros de texto
NOTA: utilización clase BufferedWriter
 */
package practicafinalpuzzle;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LineaFicherosEscritura {
    //DECLARACIONES ATRIBUTOS
    //declaración atributo de objeto BufferedWriter que posibilite el enlace
    //con el fichero de texto a nivel de escritura
    private BufferedWriter fichero=null;  
    
    
    //MÉTODOS
    
    //Métodos Constructores
    public LineaFicherosEscritura(String nombre) throws Exception {
        //establecimiento del enlace lógico con el fichero físico
        fichero=new BufferedWriter(new FileWriter(nombre));
    }
 
    public LineaFicherosEscritura(String nombre,boolean adicion) throws Exception {
        //establecimiento del enlace lógico con el fichero físico
        fichero=new BufferedWriter(new FileWriter(nombre,adicion));
    }
    
    //Métodos Funcionales
    //método que lleva a cabo la escritura de un objeto Linea en un fichero de texto
    public void escritura(Linea linea) throws Exception {
        for (int indice=0;indice<linea.numeroCaracteres();indice++) {
            fichero.write(linea.obtenerCaracter(indice));
        }
    }
    
    //método que lleva a cabo la escritura de un salto de línea en un fichero
    //de texto.
    public void escrituraSaltoDeLinea() throws Exception{
        fichero.newLine();
    }
    
    //método que lleva a cabo el cierre del enlace lógico con el fichero físico
    public void cierre() throws Exception {
        fichero.close();
    }
}
