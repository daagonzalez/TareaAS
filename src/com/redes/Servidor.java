package com.redes; /**
 * Created by B40798 on 02/11/2017.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    private Socket miServicio;
    private ServerSocket socketServicio;

    private OutputStream outputStream;
    private InputStream inputStream;

    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;

    public Servidor(int numeroPuerto){
        boolean continuar = false;
        while(!continuar){
            try {
                socketServicio = new ServerSocket(numeroPuerto);
                socketServicio.setSoTimeout(1000);
                //System.out.println("El servidor se esta escuchando en el puerto: " + numeroPuerto);
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean acepto = false;
                        while(!acepto){
                            try {
                                miServicio = socketServicio.accept();
                                acepto = true;
                            }catch (SocketTimeoutException ex){
                                acepto = false;
                            }
                            catch (IOException e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                });
                hilo.start();
                continuar = true;
            } catch (Exception ex) {
                //System.out.println("Error al abrir los sockets");
            }
        }

    }

    public void aceptar(){
        try {
            miServicio = socketServicio.accept();
        } catch (SocketTimeoutException ex){

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
        }catch (NullPointerException ex){
            //ex.printStackTrace();
        }catch (SocketException c){
            aceptar();
        }
        catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String recibirDatos() {
        String mensaje = "";
        try {
            inputStream = miServicio.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            mensaje = entradaDatos.readUTF();
            //System.out.println(mensaje);
        } catch (SocketTimeoutException ex){
            //System.out.println(ex);
            mensaje = null;
        }catch (NullPointerException ex){
            mensaje = null;
            //ex.printStackTrace();
        } catch (EOFException ex){
            //System.out.println(ex);
            mensaje = null;
        }catch (SocketException c){

        }
        catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public void cerrarTodo() {
        try {
            if(salidaDatos != null)
                salidaDatos.close();
            if(entradaDatos != null)
                entradaDatos.close();
            if(socketServicio != null)
                socketServicio.close();
            if(miServicio != null)
                miServicio.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}