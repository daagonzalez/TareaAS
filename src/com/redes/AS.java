package com.redes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AS {

    private String id;
    private ArrayList<String> rutas;
    private  Cliente client1;
    private  Servidor serv1;

    public AS(String nombreArchivo){
        this.id = "";
        this.rutas = new ArrayList<String>();
        String actualizacion;
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (nombreArchivo);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            String ip = "";
            String puerto = "";
            linea = br.readLine();
            linea = br.readLine();
            this.id = linea;
            linea = br.readLine();
            linea = br.readLine();
            if(!linea.equals("N/A"))
                rutas.add(linea+":");
            linea = br.readLine();
            while(!linea.equals("#Vecinos BGP")){
                rutas.add(linea+":");
                linea = br.readLine();
            }
            linea = br.readLine();
            if(!linea.equals("N/A")){
                ip = linea.substring(0,linea.indexOf(':'));
                puerto = linea.substring(linea.indexOf(':')+1);
                client1 = new Cliente(Integer.parseInt(puerto),ip);
            }
            while(!linea.equals("#Escuchar vecinos")){
                //Se crea el cliente
                linea = br.readLine();
            }
            linea = br.readLine();
            if(!linea.equals("N/A")){
                serv1 = new Servidor(Integer.parseInt(linea));
            }
            while(!linea.equals(null)){
                //Se crea el servidor
                linea = br.readLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        actualizacion = calcularActualizacion();
        client1.enviarDatos(actualizacion);
        serv1.recibirDatos();
        //Recibir datos
        //Actualizar


    }

    public String calcularActualizacion(){
        String actualizacion = id + "*";
        for(int i = 0; i < rutas.size(); i++) {
            if(rutas.get(i).indexOf(':') == rutas.get(i).length()-1){
                actualizacion = actualizacion + rutas.get(i) + id + ",";
            }
            else {
                String parte1 = "";
                String parte2 = "";
                parte1 = rutas.get(i).substring(0,rutas.get(i).indexOf(':')+1);
                parte2 = rutas.get(i).substring(rutas.get(i).indexOf(':')+1);//NO SIRVE Falta calcular bien la segunda parte despues del punto
                actualizacion = actualizacion + parte1 + id + "-" + parte2 + ",";
            }
        }
        return actualizacion;
    }

    public void actualizarRutas(String mensaje){

    }

}
