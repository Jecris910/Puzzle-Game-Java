/*
 * CLASE Partida - Gestionamos la lógica principal del juego de puzzle
 * Implementamos la interfaz gráfica y la interacción con el usuario

AUTOR: Cristian Morales García
 */
package practicafinalpuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Partida {

    // DECLARACIÓN DE LOS ATRIBUTOS
    // Declaramos la ventana principal del juego
    private JFrame ventana;
    // Declaramos el tablero de juego
    private Tablero tablero;   
    // Declaramos los botones de control
    private JButton btnMezclar, btnResolver, btnSalir, btnHistorial;  
    // Declaramos la barra de progreso temporal
    private JProgressBar barraTemporal;  
    // Declaramos el temporizador del juego
    private Timer cronometro;   
    // Declaramos el valor máximo para la barra de tiempo
    private final int valorMaximo = 100;   
    // Declaramos el estado de la partida
    private boolean partidaEnCurso = false;   
    // Declaramos el nombre del jugador
    private String nombreJugador = "Jugador";

    // MÉTODO CONSTRUCTOR
    public void inicializar() {
        // Configuramos la ventana principal
        ventana = new JFrame("Trencaclosques");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(650, 700);
        ventana.setLayout(new BorderLayout());

        // Creamos e inicializamos el tablero de juego
        tablero = new Tablero();
        ventana.add(tablero.getPanel(), BorderLayout.CENTER);

        // Creamos los componentes de la interfaz
        crearBotonera();
        crearBarraTemporal();

        // Implementamos el control por teclado
        ventana.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // Verificamos si hay partida en curso
                if (!partidaEnCurso) {
                    return;
                }

                // Procesamos las teclas de movimiento
                char tecla = Character.toLowerCase(e.getKeyChar());
                if (tecla == 'w') {
                    tablero.mover("S");
                } else if (tecla == 's') {
                    tablero.mover("N");
                } else if (tecla == 'a') {
                    tablero.mover("E");
                } else if (tecla == 'd') {
                    tablero.mover("O");
                }

                // Verificamos si el puzzle está resuelto
                if (tablero.estaResuelto()) {
                    cronometro.stop();
                    JOptionPane.showMessageDialog(ventana, "Has resolt el puzzle!");
                    partidaEnCurso = false;
                    barraTemporal.setValue(0);
                    guardarRegistro(true);
                }
            }
        });

        // Configuramos y mostramos la ventana
        ventana.setFocusable(true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    // MÉTODOS FUNCIONALES
    // Método para crear la botonera lateral
    private void crearBotonera() {
        // Configuramos el panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setPreferredSize(new Dimension(150, 0));
        panelBotones.setBackground(new Color(230, 240, 255));

        // Creamos los botones
        btnMezclar = new JButton("Mezclar");
        btnResolver = new JButton("Resolver");
        btnSalir = new JButton("Salir");
        btnHistorial = new JButton("Historial");

        // Configuramos los colores de los botones
        Color azulSuave = new Color(100, 149, 237);
        Color verdeSuave = new Color(144, 238, 144);
        Color rojoSuave = new Color(255, 182, 193);
        Color grisSuave = new Color(180, 200, 255);

        btnMezclar.setBackground(azulSuave);
        btnMezclar.setForeground(Color.WHITE);
        btnResolver.setBackground(verdeSuave);
        btnSalir.setBackground(rojoSuave);
        btnHistorial.setBackground(grisSuave);

        // Implementamos las acciones de los botones
        btnMezclar.addActionListener(e -> {
            // Solicitamos el nombre del jugador
            nombreJugador = JOptionPane.showInputDialog(ventana, "Nom del jugador:", "Jugador");
            if (nombreJugador == null || nombreJugador.isEmpty()) {
                nombreJugador = "Jugador";
            }
            // Mezclamos el tablero y comenzamos la partida
            tablero.mezclar();
            ventana.requestFocusInWindow();
            barraTemporal.setValue(0);
            partidaEnCurso = true;
            iniciarTemporizador();
        });

        btnResolver.addActionListener(e -> {
            // Reseteamos el tablero a la posición resuelta
            tablero.inicializar();
            ventana.requestFocusInWindow();
            barraTemporal.setValue(0);
            if (cronometro != null) {
                cronometro.stop();
            }
            partidaEnCurso = false;
        });

        btnSalir.addActionListener(e -> System.exit(0));

        btnHistorial.addActionListener(e -> Historial.mostrarHistorial());

        // Añadimos los botones al panel
        panelBotones.add(btnMezclar);
        panelBotones.add(btnResolver);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnSalir);

        // Añadimos el panel de botones a la ventana
        ventana.add(panelBotones, BorderLayout.WEST);
    }

    // Método para crear la barra de tiempo
    private void crearBarraTemporal() {
        // Configuramos la barra de progreso
        barraTemporal = new JProgressBar();
        barraTemporal.setMinimum(0);
        barraTemporal.setMaximum(valorMaximo);
        barraTemporal.setValue(0);
        barraTemporal.setStringPainted(true);
        barraTemporal.setForeground(new Color(255, 140, 0));
        barraTemporal.setBackground(new Color(245, 245, 245));
        barraTemporal.setPreferredSize(new Dimension(600, 30));
        
        // Añadimos la barra a la ventana
        ventana.add(barraTemporal, BorderLayout.SOUTH);
    }

    // Método para iniciar el temporizador
    private void iniciarTemporizador() {
        // Detenemos el temporizador si ya existe
        if (cronometro != null) {
            cronometro.stop();
        }

        // Creamos un nuevo temporizador
        cronometro = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                // Actualizamos el valor de la barra
                int valor = barraTemporal.getValue();
                if (valor < valorMaximo) {
                    barraTemporal.setValue(valor + 1);
                } else {
                    // Finalizamos la partida cuando se agota el tiempo
                    cronometro.stop();
                    JOptionPane.showMessageDialog(ventana, "Temps esgotat!");
                    partidaEnCurso = false;
                    barraTemporal.setValue(0);
                    guardarRegistro(false);
                }
            }
        });
        cronometro.start();
    }

    // Método para guardar el registro de la partida
    private void guardarRegistro(boolean haGuanyat) {
        try {
            // Abrimos el fichero de historial
            HistorialFicherosEscritura escritura = new HistorialFicherosEscritura("historial.txt", true);

            // Formateamos el resultado
            String resultat = haGuanyat ? "HA GUANYAT" : "HA PERDUT";
            String linea = "\nJugador: " + nombreJugador + " - " + resultat;

            // Escribimos y cerramos el fichero
            escritura.escribirLinea(linea);
            escritura.cerrar();
        } catch (Exception ex) {
            System.out.println("Error al guardar historial: " + ex.getMessage());
        }
    }
}