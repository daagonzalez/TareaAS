package com.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class Main3 extends Thread{

    public static AS as1;
    public static AS as2;
    public static AS as3;
    public static boolean terminar1;
    public static boolean terminar2;
    public static boolean terminar3;
    static Thread hiloInterno;
    public static String vecino1;
    public static String vecino2;
    static Timer timer;

    public static void main(String[] args) {
        /*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";*/
        as1 = new AS();
        as2 = new AS();
        as3 = new AS();
        terminar1 = false;
        terminar2 = false;
        terminar3 = false;
        vecino1 = "";
        vecino2 = "";

        /*Thread hilo3 = new Thread(new Runnable() {
            @Override
            public void run() {*/
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String input = "";
                boolean entradaInvalida;
                //try {
                    while (!input.equalsIgnoreCase("exit")) {
                        entradaInvalida = false;
                        System.out.print("Ingrese un comando> ");
                        try {
                            input = in.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        switch (input.split(" ")[0]) {
                            case "start":
                                //Thread hiloInterno = new Thread(new Runnable() {
                                hiloInterno = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        as3 = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as3.txt");
                                        /*String mensaje = "";
                                        int primer = 0;*/
                                        //while(!terminar3){

                                            TimerTask timerTask = new TimerTask() {
                                                String mensaje = "";
                                                int primer = 0;
                                                @Override
                                                public void run() {
                                                    as3.serv1.enviarDatos(as3.calcularActualizacion());
                                                    mensaje = as3.serv1.recibirDatos();

                                                    if(primer == 0 && mensaje != null && mensaje != ""){
                                                        vecino1 = mensaje.substring(0,mensaje.indexOf('*'));
                                                        primer++;
                                                        if(vecino1 == "")
                                                            primer = 0;
                                                    }

                                                    if(mensaje != null && mensaje != ""){
                                                        //vecino1 = mensaje.substring(0,mensaje.indexOf('*'));
                                                        as3.actualizarRutas(mensaje);
                                                    }
                                                    else{
                                                        if (!vecino1.equals("")) {
                                                            as3.borrarRuta(vecino1);
                                                            vecino1 = "";
                                                            primer = 0;
                                                        }
                                                    }
                                                }
                                            };
                                            timer = new Timer();
                                            timer.scheduleAtFixedRate(timerTask,0,30000);
                                            /*as3.serv1.enviarDatos(as3.calcularActualizacion());
                                            mensaje = as3.serv1.recibirDatos();

                                            if(primer == 0 && mensaje != null && mensaje != ""){
                                                vecino1 = mensaje.substring(0,mensaje.indexOf('*'));
                                                primer++;
                                                if(vecino1 == "")
                                                    primer = 0;
                                            }

                                            if(mensaje != null && mensaje != ""){
                                                //vecino1 = mensaje.substring(0,mensaje.indexOf('*'));
                                                as3.actualizarRutas(mensaje);
                                            }
                                            else{
                                                as3.borrarRuta(vecino1);
                                                vecino1 = "";
                                                primer = 0;
                                            }*/
                                            /*try {
                                                Thread.sleep(30000);
                                            }
                                            catch (InterruptedException e) {
                                                //e.printStackTrace();
                                            }*/
                                        //}
                                    }
                                });
                                hiloInterno.start();
                                //System.out.println("Iniciar");
                                break;
                            case "stop":
                                if(as3.id.equals(""))
                                    System.out.println("Debe de iniciar primero el AS \n");
                                else{
                                    /*terminar3 = true;
                                    as3.detenerAS();
                                    hiloInterno.interrupt();
                                    terminar3 = false;*/
                                    timer.cancel();
                                    System.exit(0);
                                }
                                break;
                            case "add":
                                if(as3.id.equals(""))
                                    System.out.println("Debe de iniciar primero el AS \n");
                                else{
                                    if (input.split(" ").length > 1) {
                                        String idRed = input.substring(input.indexOf(" ")+1);
                                        boolean doble = false;
                                        for(int i = 0; i < as3.rutas.size(); i++){
                                            if(as3.rutas.get(i).contains(idRed))
                                                doble = true;
                                        }
                                        if(!doble)
                                            as3.agregarRed(idRed);
                                        else
                                            System.out.println("Esta ruta ya existe \n");
                                    }
                                }
                                break;
                            case "show":
                                if (input.split(" ").length > 1) {
                                    if (input.split(" ")[1].equals("routes")) {
                                        if(as3.id.equals(""))
                                            System.out.println("Debe de iniciar primero el AS \n");
                                        else
                                            as3.mostrarRutas();

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
        /*        } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        hilo3.start();*/


        /*CyclicBarrier barrera = new CyclicBarrier(3);

        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as1.txt");
                String mensaje = "";
                    try {
                        barrera.await();//ESPERA A QUE LOS OTROS 2 HILOS TERMINEN DE CREAR LOS CLIENTES O SERVIDORES
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    while(true) {
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
                AS as = new AS("C:\\Users\\Ballestero-Cabezas\\IdeaProjects\\TareaAS\\src\\com\\redes\\as2.txt");
                String mensajeC = "";
                String mensajeS = "";
                    try {
                        barrera.await();//ESPERA A QUE LOS OTROS 2 HILOS TERMINEN DE CREAR LOS CLIENTES O SERVIDORES
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    while(true) {
                        as.client1.enviarDatos(as.calcularActualizacion());
                        mensajeC = as.client1.escucharDatos(as.client1.socketCliente);

                        as.serv1.enviarDatos(as.calcularActualizacion());
                        mensajeS = as.serv1.recibirDatos();

                        as.actualizarRutas(mensajeC);
                        as.actualizarRutas(mensajeS);

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
                    try {
                        barrera.await();//ESPERA A QUE LOS OTROS 2 HILOS TERMINEN DE CREAR LOS CLIENTES O SERVIDORES
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    while(true) {
                        as.serv1.enviarDatos(as.calcularActualizacion());
                        mensaje = as.serv1.recibirDatos();

                        as.actualizarRutas(mensaje);
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
        hilo3.start();
*/
    }
}