package com.redes;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AS {

    public String id;
    public ArrayList<String> rutas;
    public Cliente client1;
    public Servidor serv1;
    Map<String,Integer> cantidadVec;

    public AS() {
        this.id = "";
    }

    public AS(String nombreArchivo){
        this.id = "";
        this.rutas = new ArrayList<String>();
        this.cantidadVec = new HashMap<String,Integer>();
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
            //e.printStackTrace();
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
        return actualizacion.substring(0,actualizacion.length()-1);
    }

    public void actualizarRutas(String mensaje){
        //System.out.println(mensaje);
        if(!mensaje.endsWith(",")){
            mensaje += ',';
        }
        String ruta = "";
        String enlace = "";
        String idRed = "";
        String rutaAS = "";
        String idAS = "";
        ArrayList<String> redesRecibidas = new ArrayList<String>();

        //Mapa de AS con sus vecinos

        Pattern p = Pattern.compile( "AS\\d" );
        // Obtener el ID del AS del mensaje que se recibió
        Matcher m = p.matcher( mensaje );
        if ( m.find() ) {
            idAS = m.group(0);
            if (!cantidadVec.containsKey(idAS))
                cantidadVec.put(idAS,0);
        }
        // Contar los vecinos del AS según el mensaje
        ruta = mensaje.substring(mensaje.indexOf('*')+1);
        int cantidadM = 0;
        m = p.matcher(ruta);
        while (m.find()) {
            cantidadM++;
        }


        while(ruta.length() > 0) {
            enlace = ruta.substring(0, ruta.indexOf(','));
            idRed = enlace.substring(0, ruta.indexOf(':') + 1);
            redesRecibidas.add(enlace.substring(0, ruta.indexOf(':')));
            if (!enlace.contains(id)) {
                //idRed = enlace.substring(0, ruta.indexOf(':') + 1);
                if (ruta.indexOf(':') != ruta.length() - 1) {

                    if (!rutas.contains(idRed) && !rutas.contains(enlace)) {
                        rutas.add(enlace);
                    }
                }
            }
            ruta = ruta.substring(ruta.indexOf(',') + 1);
        }
        if (cantidadM >= cantidadVec.get(idAS)) {
            cantidadVec.put(idAS,cantidadM);
        } else {
            cantidadVec.put(idAS,cantidadM);
            for(int i = 0 ; i < rutas.size(); i++){
                //for(int j = 0 ; j < redesRecibidas.size(); j++){
                String idred = rutas.get(i).substring(0,rutas.get(i).indexOf(':'));
                //System.out.println(idred);
                //System.out.println(redesRecibidas.get(0));
                    if(!redesRecibidas.contains(idred)){
                        if(rutas.get(i).contains(idAS)){
                            rutas.remove(i);
                            i--;
                        }
                    }
                //}
            }
        }
    }

    public void borrarRuta(String idVecino){
        for(int i = 0; i < rutas.size(); i++){
            if(rutas.get(i).contains(idVecino) && idVecino != ""){
                rutas.remove(i);
                i--;
            }
        }
    }

    public void agregarRed(String idRed){

        rutas.add(idRed + ":");
    }

    public void detenerAS(){
        rutas.clear();
        id = "";
        if(client1 != null)
            client1.cerrarTodo();
        if(serv1 != null)
            serv1.cerrarTodo();
    }

    public void mostrarRutas(){
        System.out.println("RUTAS CONOCIDAS \n");
        for(int i = 0; i < rutas.size(); i++){
            if(rutas.get(i).indexOf(':') ==  rutas.get(i).length()-1)
                System.out.println(rutas.get(i) + " Propia \n");
            else
                System.out.println(rutas.get(i) + "\n");
        }
    }

}
