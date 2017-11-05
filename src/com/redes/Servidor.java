package com.redes; /**
 * Created by B40798 on 02/11/2017.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Servidor {

    private Socket miServicio;
    private ServerSocket socketServicio;

    private OutputStream outputStream;
    private InputStream inputStream;

    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;

    private boolean opcion = true;
    private Scanner scanner;
    private String esctribir;

    //APERTURA DE SOCKET
    /*public void conexion(int numeroPuerto) {
        try {
            socketServicio = new ServerSocket(numeroPuerto);
            System.out.println("El servidor se esta escuchando en el puerto: " + numeroPuerto);
            miServicio = socketServicio.accept();
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (opcion) {
                        System.out.print("SERVIDOR: ");
                        recibirDatos();

                    }
                }
            });
            hilo.start();
            while (opcion) {
                scanner = new Scanner(System.in);
                esctribir = scanner.nextLine();
                if (!esctribir.equals("CLIENTE: fin")) {
                    enviarDatos("SERVIDOR: " + esctribir);
                    //recibirDatos();
                } else {
                    opcion = false;
                }
            }
            miServicio.close();
        } catch (Exception ex) {
            System.out.println("Error al abrir los sockets");
        }
    }*/

    public Servidor(int numeroPuerto){
        try {
            socketServicio = new ServerSocket(numeroPuerto);
            System.out.println("El servidor se esta escuchando en el puerto: " + numeroPuerto);
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        miServicio = socketServicio.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
        } catch (Exception ex) {
            System.out.println("Error al abrir los sockets");
        }
    }

    public void aceptar(){
        try {
            socketServicio.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarDatos(String datos) {
        try {
            outputStream = miServicio.getOutputStream();
            salidaDatos = new DataOutputStream(outputStream);
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String recibirDatos() {
        String mensaje = "";
        try {
            inputStream = miServicio.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            mensaje = entradaDatos.readUTF();
            System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public void cerrarTodo() {
        try {
            salidaDatos.close();
            entradaDatos.close();
            socketServicio.close();
            miServicio.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public static void main(String[] args) {
        Servidor serv = new Servidor();
        serv.conexion(5555);
    }*/

}