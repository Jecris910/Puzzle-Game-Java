/*
 * CLASE Tablero - Implementa el tablero de juego para un puzzle deslizante 3x3
 * Gestiona la lógica del juego y la representación visual de las piezas
 */
package practicafinalpuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Tablero {

    //DECLARACIÓN DE LOS ATRIBUTOS
    //declaración matriz 3x3 de objetos Casilla que representa las piezas del puzzle
    private Casilla[][] casillas; 
    //declaración objeto JPanel que contiene la representación gráfica del tablero
    private JPanel panel;
    //declaración matriz 3x3 de JLabels para visualizar cada pieza del puzzle
    private JLabel[][] labels;
    //declaración variable entera que almacena la fila de la casilla vacía
    private int filaVacia;
    //declaración variable entera que almacena la columna de la casilla vacía
    private int columnaVacia;
    //declaración array de BufferedImage que almacena las 9 partes de la imagen dividida
    private BufferedImage[] partesImagen;

    //MÉTODO CONSTRUCTOR
    public Tablero() {
        //instanciación del panel con GridLayout de 3 filas y 3 columnas
        panel = new JPanel(new GridLayout(3, 3));
        
        //implementación del listener para redimensionar las imágenes al cambiar tamaño
        //añadimos esto para redimensionar la imagen para evitar possibles problemas de redimensión
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                //llamada al método para actualizar la vista al redimensionar
                actualizarVista();
            }
        });
        
        //instanciación de la matriz 3x3 de JLabels
        labels = new JLabel[3][3];
        
        //llamada al método para cargar y dividir la imagen
        cargarImagen();
        
        //llamada al método para inicializar el tablero
        inicializar();
    }

    //MÉTODOS FUNCIONALES
    //método cargarImagen lleva a cabo la carga y división de la imagen en 9 partes
    private void cargarImagen() {
        try {
            //carga de la imagen completa desde el fichero
            Image imagenCompleta = new ImageIcon("MarioBros.jpg").getImage();
            
            //instanciación del array para almacenar las partes de la imagen
            partesImagen = new BufferedImage[9];
            
            //cálculo del ancho y alto de cada parte
            int ancho = imagenCompleta.getWidth(null) / 3;
            int alto = imagenCompleta.getHeight(null) / 3;

            //bucle para dividir la imagen en 9 partes (3x3)
            //este metodo permite corrtar la imagen para permitir que se divida en
            //9 y podamos usarla correctamnete en el puzzle sin problemas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //creación de cada parte como BufferedImage
                    BufferedImage imagenBuffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = imagenBuffer.createGraphics();
                    
                    //dibujado de la porción correspondiente de la imagen
                    g.drawImage(imagenCompleta,0, 0, ancho, alto,
                            j * ancho, i * alto,(j + 1) * ancho, (i + 1) * alto,
                            null);
                    g.dispose();
                    
                    //almacenamiento de la parte en el array
                    partesImagen[i * 3 + j] = imagenBuffer;
                }
            }
        } catch (Exception e) {
            //mostrar mensaje de error si no se puede cargar la imagen
            JOptionPane.showMessageDialog(null, "No se pudo cargar la imagen: MarioBros.jpg");
            System.exit(1);
        }
    }

    //método inicializar lleva a cabo la configuración inicial del tablero
    public void inicializar() {
        //limpieza del panel para nueva configuración
        panel.removeAll();
        
        //instanciación de la matriz 3x3 de casillas
        casillas = new Casilla[3][3];
        int valor = 1;

        //bucle para inicializar cada casilla y su representación visual
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //asignación del valor 0 a la última casilla (casilla vacía)
                if (valor == 9) {
                    valor = 0;
                }
                
                //creación de la casilla con valor y posición
                casillas[i][j] = new Casilla(valor, i, j);

                //configuración del JLabel correspondiente
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //asignación de imagen o color según el valor
                if (valor != 0) {
                    int ancho = panel.getWidth() / 3;
                    int alto = panel.getHeight() / 3;
                    if (ancho <= 0 || alto <= 0) {
                        ancho = 200;
                        alto = 200;
                    }
                    Image escalada = partesImagen[valor - 1].getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    labels[i][j].setIcon(new ImageIcon(escalada));
                } else {
                    labels[i][j].setBackground(Color.LIGHT_GRAY);
                }

                //añadir el JLabel al panel
                panel.add(labels[i][j]);

                //registro de la posición de la casilla vacía
                if (valor == 0) {
                    filaVacia = i;
                    columnaVacia = j;
                }
                valor++;
            }
        }

        //actualización del panel
        panel.revalidate();
        panel.repaint();
    }

    //método mezclar lleva a cabo la aleatorización de las piezas del puzzle
    //USAMOS EL ALGORITMO DE FISHER-YATES
    public void mezclar() {
        //creación de array con valores del 1 al 8 y 0 (casilla vacía)
        int[] valores = new int[9];
        for (int i = 0; i < 8; i++) {
            valores[i] = i + 1;
        }
        valores[8] = 0;

        //algoritmo de Fisher-Yates para mezclar los valores
        Random rand = new Random();
        for (int i = valores.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = valores[i];
            valores[i] = valores[j];
            valores[j] = temp;
        }

        //reconfiguración del tablero con los valores mezclados
        panel.removeAll();
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int valor = valores[index];
                casillas[i][j] = new Casilla(valor, i, j);

                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //asignación de imagen 
                if (valor != 0) {
                    Image escalada = partesImagen[valor - 1].getScaledInstance(panel.getWidth() / 3, panel.getHeight() / 3, Image.SCALE_SMOOTH);
                    labels[i][j].setIcon(new ImageIcon(escalada));
                } else {
                    labels[i][j].setBackground(Color.LIGHT_GRAY);
                    filaVacia = i;
                    columnaVacia = j;
                }
                panel.add(labels[i][j]);
                index++;
            }
        }

        //actualización del panel
        panel.revalidate();
        panel.repaint();
    }

    //método mover lleva a cabo el desplazamiento de piezas según dirección
    public void mover(String direccion) {
        //inicialización de nuevas coordenadas con posición actual vacía
        int nuevaFila = filaVacia;
        int nuevaCol = columnaVacia;

        //actualización de coordenadas según dirección
        if (direccion.equals("N")) {
            nuevaFila = filaVacia - 1;
        } else if (direccion.equals("S")) {
            nuevaFila = filaVacia + 1;
        } else if (direccion.equals("E")) {
            nuevaCol = columnaVacia + 1;
        } else if (direccion.equals("O")) {
            nuevaCol = columnaVacia - 1;
        }

        //verificación de límites del tablero
        if (nuevaFila >= 0 && nuevaFila < 3 && nuevaCol >= 0 && nuevaCol < 3) {
            //intercambio de valores entre casillas
            int temp = casillas[nuevaFila][nuevaCol].getValor();
            casillas[nuevaFila][nuevaCol].setValor(casillas[filaVacia][columnaVacia].getValor());
            casillas[filaVacia][columnaVacia].setValor(temp);
            
            //actualización de posición vacía
            filaVacia = nuevaFila;
            columnaVacia = nuevaCol;
            
            //actualización de la vista
            actualizarVista();
        }
    }

    //método actualizarVista lleva a cabo la actualización gráfica del tablero
    private void actualizarVista() {
        //limpieza del panel para nueva configuración
        panel.removeAll();
        
        //bucle para actualizar cada JLabel según estado actual
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int valor = casillas[i][j].getValor();

                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //asignación de imagen 
                if (valor != 0) {
                    Image escalada = partesImagen[valor - 1].getScaledInstance(panel.getWidth() / 3, panel.getHeight() / 3, Image.SCALE_SMOOTH);
                    labels[i][j].setIcon(new ImageIcon(escalada));
                } else {
                    labels[i][j].setBackground(Color.LIGHT_GRAY);
                }
                panel.add(labels[i][j]);
            }
        }
        
        //actualización del panel
        panel.revalidate();
        panel.repaint();
    }

    //método estaResuelto verifica si el puzzle está en posición de solución
    public boolean estaResuelto() {
        int esperado = 1;
        
        //bucle para verificar posición y valor de cada casilla
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) { 
                int valor = casillas[i][j].getValor();
                
                //verificación de última casilla 
                if (i == 2 && j == 2) {
                    if (valor != 0) {
                        return false;
                    }
                } else {
                    //verificación de secuencia correcta (1-8)
                    if (valor != esperado) {
                        return false;
                    }
                    esperado++;
                }
            }
        }
        return true;
    }

    //método getPanel devuelve el panel principal del tablero
    public JPanel getPanel() {
        return panel;
    }
}