/*
Clase Linea --> aglutina las declaraciones y funcionalidades para gestionar
objetos Lineas.
Un objeto Linea estará constituido por una secuencia de caracteres delimitado por
un salto de linea. Como máximo el número de caracteres que puede conformar un
objeto Linea será de 250 caracteres.
*/
package practicafinalpuzzle;

import java.util.Scanner;

public class Linea {

    //DECLARACIONES
    //declaración atributo de clase constante entero que representa el máximo
    //número de caracteres que puede contener un objetoLinea
    private static final int MAXIMO = 255;
    //declaración atributo de objeto variable array de caracteres que representa 
    //los caracteres de un objeto Linea 
    private char[] caracteres = new char[MAXIMO];
    //declaración atributo de objeto variable entero que represente el número
    //de caracteres de un objeto Linea
    private int numeroCaracteres;
    //Scanner para leer la entrada
    private static Scanner scanner = new Scanner(System.in);

    //MÉTODOS
    //Métodos Constructores
    public Linea() {
        numeroCaracteres = 0;
    }

    public Linea(String dato) {
        //DECARACIONES
        //declaración array de componentes char para almacenar los caracteres
        //contenidos en el String dado por parámetro
        char[] arrayDato = dato.toCharArray();
        if (arrayDato.length <= 255) {
            for (int indice = 0; indice < arrayDato.length; indice++) {
                caracteres[indice] = arrayDato[indice];
            }
            numeroCaracteres = arrayDato.length;
        }
    }

    //Métodos funcionales
    //método que lleva a cabo la verificación de si una objeto Linea ha sido
    //totalmente leido desde el teclado
    public static boolean hayLinea() {
        return scanner.hasNextLine();
    }

    //método que lleva a cabo la lectura de una linea desde el teclado
    public void lectura() {
        String linea = scanner.nextLine();
        char[] arrayLinea = linea.toCharArray();
        
        numeroCaracteres = Math.min(arrayLinea.length, MAXIMO);
        for (int i = 0; i < numeroCaracteres; i++) {
            caracteres[i] = arrayLinea[i];
        }
    }

    //método que lleva a cabo la conversión de un objeto Linea a String para su
    //visualización en pantalla
    @Override
    public String toString() {
        String salida = "";
        for (int indice = 0; indice < numeroCaracteres; indice++) {
            salida = salida + caracteres[indice];
        }
        return salida;
    }
    
    //método que devuelve el número de caracteres que conforman un objeto Linea
    public int numeroCaracteres() {
        return numeroCaracteres;
    }

    public void adicionNumero(int num) {
        if (num > 9 && num < 100) {
            int numero1 = num / 10;
            int numero2 = num - (numero1 * 10);
            adicionCaracter(numero1 + 48);
            adicionCaracter(numero2 + 48);
        } else {
            adicionCaracter(num + 48);
        }
    }

    //método que lleva a cabo la adición de un caracter dado por parámetro
    //al objeto Linea
    public void adicionCaracter(int cod) {
        if (numeroCaracteres < MAXIMO) {
            caracteres[numeroCaracteres] = (char) cod;
            numeroCaracteres++;
        }
    }

    //metodo el cual añade una linea pasada por parametro seguido de otra linea
    public void adicionLinea(Linea linea) {
        for (int i = 0; i < linea.numeroCaracteres && numeroCaracteres < MAXIMO; i++) {
            this.adicionCaracter((int) linea.obtenerCaracter(i));
        }
    }

    //método que lleva a cabo la obtención del caracter del objeto Linea en función
    //de la posición dada por parámetro
    public char obtenerCaracter(int pos) {
        return caracteres[pos];
    }
    
    //METODO QUE VERIFICA SI DOS LINEAS SON IGUALES
    public boolean igual(Linea linea) {
        //verificar si tienen el mismo número de caracteres
        if (numeroCaracteres == linea.numeroCaracteres) {
            //verificación de si son iguales caracter a caracter
            for (int indice = 0; indice < numeroCaracteres; indice++) {
                if (caracteres[indice] != linea.caracteres[indice]) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}