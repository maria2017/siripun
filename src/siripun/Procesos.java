/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siripun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Procesos {

    DataOutputStream dos;
    DataInputStream dis;
    
    public Procesos(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis; //Entrada -> cliente => servidor
        this.dos = dos; //Salida -> servidor => cliente
    }

    public void enviarMensajeBienvenida() {
        enviarMensaje("Bienvenido, soy SIRIPUN\n" +
                "Intentaré contestar a todas tus preguntas.\n" +
                "Para salir introduzca el signo '?'\n");
    }
    
    public void procesarPregunta(String pregunta) {
        enviarMensaje("Ud. ha dicho: " + pregunta+"\n");
    }

    public String recibirMensaje() {
        String salida="";
        try {
            salida=dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salida;
    }
    
    public void enviarMensaje(String mensaje) {
        try {
            dos.writeUTF(mensaje);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
