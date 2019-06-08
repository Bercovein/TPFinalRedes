package com.company.socketTcp.domain;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {

        // https://www.youtube.com/watch?v=EnVrxz2iVSo

        final int PORT = 3000;
        ServerSocket server;

        try {

            server = new ServerSocket(PORT); //crea el servidor
            System.out.println("-->Servidor iniciado<--");

            while (true) {

                Socket sc = server.accept(); //espera al cliente

                String client = sc.getInetAddress().getHostName() + "::" + sc.getPort();

                System.out.println("Cliente " + client + " -> Conectado");

                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                String mensaje;

                while(!sc.isClosed()){
                    mensaje = in.readUTF();
                    System.out.println(sc.getInetAddress().getHostName() + "::" + sc.getPort() + " = " + mensaje);
                    out.writeUTF("✓✓");

                    if (mensaje.toLowerCase().startsWith("x")){
                        out.writeUTF(" -> Desconectado.");
                        sc.close();
                    }
                }

                System.out.println("Cliente " + client + " -> Desconectado");
            }
        }catch (SocketException soc){
            System.out.println("El socket esta desconectado.");
        }
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
