/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siripun;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author maria
 */
public class Cliente {

    private static final int PUERTO = 8000;
    private static final String SERVER = "localhost";

    public static void main(String[] args) {
        try {
            System.out.println("*************************");
            System.out.println("*** CLIENTE: iniciando...");
            // Flujo de entrada para solicitar las preguntas
            // por teclado
            Scanner sc = new Scanner(System.in);

            // Creación del socket cliente
            Socket clientSocket = new Socket();

            // Asignación de la dirección y puerto (BIND)
            InetSocketAddress addr = new InetSocketAddress(SERVER, PUERTO);

            // Conexión al socket servidor
            // Tiene que haber un ServerSocket escuchando en ese puerto
            clientSocket.connect(addr);

            // Flujo de salida hacia el servidor (envío del mensaje)
            DataOutputStream flujoSalida = new DataOutputStream(
                    clientSocket.getOutputStream());

            // Flujo de entrada para recibir mensajes del servidor
            DataInputStream flujoEntrada = new DataInputStream(
                    clientSocket.getInputStream());

            Boolean terminar = false;
            String respuestaServidor;
            respuestaServidor=flujoEntrada.readUTF();
            System.out.println(respuestaServidor);
            while (!terminar) {
                //respuestaServidor=flujoEntrada.readUTF();
respuestaServidor="";
while(flujoEntrada.available() > 0) {
    respuestaServidor+=flujoEntrada.readUTF();
}




                //Evitamos mostar mensaje de cierre "."
                if(!respuestaServidor.equals(".")) {
                    System.out.println(respuestaServidor);
                    String pregunta = sc.nextLine();
                    flujoSalida.writeUTF(pregunta);
                    flujoSalida.flush();
                } else {
                    terminar = true;
                }
            }

            System.out.println("*** Cerrando cliente");
            System.out.println("********************");
            
            // Cierre de flujos de datos
            sc.close();
            flujoEntrada.close();
            flujoSalida.close();

            // Cierre de conexiones
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
