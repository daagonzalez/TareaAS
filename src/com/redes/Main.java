package com.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            while (!input.equalsIgnoreCase("exit")) {
                System.out.print("Ingrese un comando> ");
                input = in.readLine();
                if(input.equals("start")) {
                    System.out.println("Inicia el funcionamiento del router.");
                }
                else if(input.equals("stop")) {
                    System.out.println("Detiene el funcionamiento del router. (No soportado)");
                }
                else if(input.equals("add")) {
                    System.out.println("Agrega una red directamente conectada al router (No soportado).");
                }
                else if(input.equals("show routes")) {
                    System.out.println("Muestra todas las rutas aprendidas y agrega un * a las que deban usarse.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}