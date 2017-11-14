package com.redes;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public Socket socketCliente;

    private InputStream inputStream;
    private OutputStream outputStream;

    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;

    private String nIp;
    private int nPuerto;

    public Cliente(int numeroPuerto, String ipMaquina){
        nPuerto = numeroPuerto;
        nIp = ipMaquina;
        boolean continuar = false;
        while(!continuar){
            try {
                socketCliente = new Socket(nIp,nPuerto);
                socketCliente.setSoTimeout(1000);
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
        } catch (SocketException c){

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
        } catch (SocketException c){
            try {
                socketCliente = new Socket(nIp, nPuerto);
            } catch (SocketException ex) {
                //e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}