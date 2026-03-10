/*
CLASE Historial permite visualizar el historial de partidas jugadas en una ventana.
Lee el contenido desde un fichero de texto y lo muestra en un área de texto desplazable.
*/
package practicafinalpuzzle;

import javax.swing.*;
import java.awt.*;

public class Historial {
    
    // MÉTODO mostrarHistorial muestra una ventana con el contenido del historial de partidas
    public static void mostrarHistorial() {
        // creación de la ventana principal con título
        JFrame ventana = new JFrame("Historial de Partides");
        ventana.setSize(600, 400); // tamaño de la ventana
        ventana.setLocationRelativeTo(null); // centra la ventana en la pantalla

        // creación de un área de texto para mostrar el historial
        JTextArea area = new JTextArea();
        area.setEditable(false); // el área de texto es solo de lectura
        JScrollPane scroll = new JScrollPane(area); // se añade scroll al área de texto

        try {
            // creación del lector para acceder al contenido del fichero historial.txt
            HistorialFicherosLectura lector = new HistorialFicherosLectura("historial.txt");
            // lectura del contenido completo del historial
            String contenido = lector.leerHistorialCompleto();
            lector.cerrar(); // cierre del fichero tras la lectura
            // se establece el contenido leído en el área de texto
            area.setText(contenido);
        } catch (Exception e) {
            // en caso de error, se muestra un mensaje en el área de texto
            area.setText("No s'ha pogut carregar l'historial.\n" + e.getMessage());
        }

        // se añade el área de texto con scroll a la ventana
        ventana.add(scroll);
        ventana.setVisible(true); // se hace visible la ventana
    }
}
