package com.company.socketTcp.domain;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

    ServerThread() { }

    public void run() {

        try {
            while (true) {

                Server server = Server.getInstance(); //toma una instancia de la clase servidor

                Socket sc = server.getSocket().accept(); //espera a que algun cliente se conecte

                server.getClients().add(sc);

                //asigna address al cliente
                String client = sc.getInetAddress().getHostAddress();

                //printea que cliente se conectó
                System.out.println("Cliente [" + client + "] -> Conectado");

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                String mensaje;

                while (!sc.isClosed()) {
                    mensaje = in.readUTF();
                    System.out.println("[" + sc.getInetAddress().getHostAddress() + "]: " + mensaje);
                    out.writeUTF("✓✓");

                    if (mensaje.toLowerCase().startsWith("x")) {
                        server.getClients().remove(sc);
                        out.writeUTF(server.getServerName() + " -> Desconectado.");
                        sc.close();
                    }
                }

                System.out.println("Cliente " + client + " se ha desconectado de " + server.getServerName() + ".");
            }
        } catch (SocketException soc) {
            soc.printStackTrace();
            System.out.println("El socket esta desconectado.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
