package com.company;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PORT = 3000;

        try {
            //Creo el socket para conectarme con el servidor
            Socket sc = new Socket(HOST, PORT);

            if(sc.isConnected()) {

                System.out.println("Conexión establecida con: " + HOST + " por el puerto: " + PORT);

                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                //Recibo el mensaje del servidor
                String ack;

                String mensaje;
                boolean exit;

                do {
                    mensaje = console.readLine();
                    out.writeUTF(mensaje);
                    ack = in.readUTF();
                    System.out.println(ack);
                    exit = mensaje.toLowerCase().startsWith("x");
                    if(exit){
                        ack = in.readUTF();
                        System.out.println(ack);
                    }
                } while (!exit);

                in.close();
                out.close();
                sc.close();
            }

        } catch (ConnectException con){
            System.out.println("No se pudo acceder al servidor");
        } catch (SocketException soc){
            System.out.println("Se ha perdido conexión con el servidor.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
