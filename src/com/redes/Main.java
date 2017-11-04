package com.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main extends Thread{

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        /*AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as1.txt");
        while(true){

        }*/
        CyclicBarrier barrera = new CyclicBarrier(3);


        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as2.txt");
                String mensaje = "";
                while(true){
                    try {
                        barrera.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    as.client1.enviarDatos(as.calcularActualizacion());
                    mensaje = as.client1.escucharDatos(as.client1.socketCliente);
                    as.actualizarRutas(mensaje);
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo.start();

        Thread hilo2 = new Thread(new Runnable() {
            @Override
            public void run() {
                AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as1.txt");
                String mensaje = "";
                while(true){
                    try {
                        barrera.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    as.client1.enviarDatos(as.calcularActualizacion());
                    mensaje = as.client1.escucharDatos(as.client1.socketCliente);
                    as.actualizarRutas(mensaje);
                    mensaje = as.serv1.recibirDatos();
                    as.actualizarRutas(mensaje);
                    as.serv1.enviarDatos(as.calcularActualizacion());
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo2.start();

        Thread hilo3 = new Thread(new Runnable() {
            @Override
            public void run() {
                AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as3.txt");
                String mensaje = "";
                while(true){
                    try {
                        barrera.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    mensaje = as.serv1.recibirDatos();
                    as.actualizarRutas(mensaje);
                    as.serv1.enviarDatos(as.calcularActualizacion());
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo3.start();
        //new Servidor().main(null);
        //new Cliente().main(null);
        /*boolean entradaInvalida;
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
        }*/
    }
}