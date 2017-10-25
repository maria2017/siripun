/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siripun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author maria
 */
public class Servidor {

    private static final int PUERTO = 8000;

    public static void main(String[] args) {
        try {
            // Creación del socket servidor (puerto localhost)
            // Asignación de dirección y puerto (BIND)
            ServerSocket serverSocket = new ServerSocket(PUERTO);

            // Espera/escucha del cliente (LISTEN) y aceptación de conexiones
            // (ACCEPT)
            System.out.println("***********************************************");
            System.out.println("*** SERVIDOR: Escuchando por el puerto "
                    + PUERTO + " ...");
            //Bloquea la ejecución hasta que se crea la conexión
            Socket clientSocket = serverSocket.accept();

            // Flujo de entrada del cliente (recepción del mensaje)
            DataInputStream flujoEntrada = new DataInputStream(
                    clientSocket.getInputStream());

            // Flujo de salida hacia el cliente (envío de las respuestas)
            DataOutputStream flujoSalida = new DataOutputStream(
                    clientSocket.getOutputStream());

            Procesos procesos = new Procesos(flujoEntrada, flujoSalida);

            //Enviamos mensaje de bienvenida
            procesos.enviarMensajeBienvenida();
            System.out.println("*** Enviado mensaje de bienvenida");
            
            Boolean terminar = false;
            String respuestaCliente = "";
            while (!terminar) {
                procesos.enviarMensaje("¿En qué puedo ayudarte?\n");
                respuestaCliente = procesos.recibirMensaje();
                //Evitamos mostrar mensaje para salir "?"
                if(!respuestaCliente.equals("?")) {
                    procesos.procesarPregunta(respuestaCliente);
                    System.out.println("MENSAJE RECIBIDO: " + respuestaCliente);
                } else {
                    terminar = true;
                }
            }

            procesos.enviarMensaje("¡Hasta otra!");
            //Enviamos mensaje de cierre al cliente
            procesos.enviarMensaje(".");
            
            System.out.println("*** CERRANDO SERVIDOR...");
            System.out.println("************************");

            // Cierre de flujos y conexión (en orden)
            flujoEntrada.close();
            flujoSalida.close();

            // Cierre de conexión del cliente
            clientSocket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
