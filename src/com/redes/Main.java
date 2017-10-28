package com.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        boolean entradaInvalida;
        try {
            while (!input.equalsIgnoreCase("exit")) {
                entradaInvalida = false;
                System.out.print("Ingrese un comando> ");
                input = in.readLine();
                switch (input.split(" ")[0]) {
                    case "start":
                        System.out.println("Iniciar");
                        break;
                    case "stop":
                        System.out.println("Detener (No soportado)");
                        break;
                    case "add":
                        //Revisar que después de add venga una red válida
                        System.out.println("Agregar una red (No soportado).");
                        break;
                    case "show":
                        if (input.split(" ").length > 1) {
                            if (input.split(" ")[1].equals("routes")) {
                                System.out.println("Muestra todas las rutas");
                            }
                            else {
                                entradaInvalida = true;
                            }
                        }
                        else {
                            entradaInvalida = true;
                        }
                        break;
                    case "help":
                        System.out.println( "\tstart:\t\tInicia el funcionamiento del enrutador.\n" +
                                "\tstop:\t\tDetiene el funcionamiento del enrutador. Esto elimina toda la\n" +
                                "información de rutas y detiene la comunicación con los vecinos.\n" +
                                "\tadd <subred>:\tAgrega una red directamente conectada al enrutador.\n" +
                                "Este debe propagar la información a los demás AS’s.\n" +
                                "\tshow routes:\tMuestra en pantalla todas las rutas aprendidas y agrega *\n" +
                                "a las que deberían usarse (menor cantidad de AS’s).\n" +
                                "El formato requerido es el siguiente:\n" +
                                "red <dir red> : AS<num> - AS<num> - AS<num>\n" +
                                "\thelp:\t\tMuestra esta ayuda.\n" +
                                "\texit:\t\tTermina la ejecución de la aplicación.\n");
                        break;
                    default:
                        if (!input.equals("exit")) {
                            entradaInvalida = true;
                        }
                        break;
                }
                if(entradaInvalida) {
                    System.out.println("Comando inválido. Escriba 'help' para ver una lista de los comandos aceptados.\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}