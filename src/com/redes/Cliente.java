package com.redes;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Cliente {

    public Socket socketCliente;

    private InputStream inputStream;
    private OutputStream outputStream;

    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;

    private boolean opcion = true;

    private Scanner scanner;
    private String esctribir;

    public Cliente(int numeroPuerto, String ipMaquina){
        boolean continuar = false;
        while(!continuar){
            try {
                socketCliente = new Socket(ipMaquina, numeroPuerto);
                socketCliente.setSoTimeout(30000);
                //System.out.println("El cliente se conecto a puerto: " + numeroPuerto);
                Thread hilo1 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                hilo1.start();
                continuar = true;
            } catch (Exception ex) {
                //System.out.println("ESPERANDO QUE SE CREE SERVIDOR" + ex.getMessage());
                //System.out.println("ESPERANDO QUE SE CREE SERVIDOR");
            }
        }

    }

    public String escucharDatos(Socket socket) {
        String mensaje = "";
        try {
            inputStream = socket.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            mensaje = entradaDatos.readUTF();
            //System.out.println(mensaje);
        } catch (SocketTimeoutException ex){
            //System.out.println(ex);
            mensaje = null;
        } catch (EOFException ex){
            //System.out.println(ex);
            mensaje = null;
        } catch (IOException ex) {
            //System.out.println(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }
    public void enviarDatos(String datos) {
        try {
            outputStream = socketCliente.getOutputStream();
            salidaDatos = new DataOutputStream(outputStream);
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void cerrarTodo() {
        try {
            if(salidaDatos != null)
                salidaDatos.close();
            if(entradaDatos != null)
                entradaDatos.close();
            if(socketCliente != null)
                socketCliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public void conexion(int numeroPuerto, String ipMaquina) {
        try {
            socketCliente = new Socket(ipMaquina, numeroPuerto);
            Thread hilo1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (opcion) {
                        escucharDatos(socketCliente);
                        System.out.print("CLIENTE: ");
                    }
                }
            });
            hilo1.start();
            while (opcion) {
                scanner = new Scanner(System.in);
                esctribir = scanner.nextLine();
                if (!esctribir.equals("SERVIDOR: fin")) {
                    enviarDatos("CLIENTE: " + esctribir);
                    //escucharDatos(socketCliente);
                } else {
                    opcion = false;
                    cerrarTodo();
                }
            }

        } catch (Exception ex) {
            System.out.println("ERROR AL ABRIR LOS SOCKETS CLIENTE " + ex.getMessage());
        }
    }
    public void escucharDatos(Socket socket) {
        try {
            inputStream = socket.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            System.out.println(entradaDatos.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void enviarDatos(String datos) {
        try {
            outputStream = socketCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF(datos);
            SalidaDatos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void cerrarTodo() {
        try {
            SalidaDatos.close();
            entradaDatos.close();
            socketCliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.conexion(5555, "localhost");
    }*/
}