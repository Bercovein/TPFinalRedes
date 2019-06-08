package com.company.socketTcp.domain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    Server server;

    ServerThread(Server server) {
        this.server = server;
    }
    public void run() {

        try {
            while (true) {

                Socket sc = server.getServer().accept(); //espera al cliente

                String client = sc.getInetAddress().getHostName() + "::" + sc.getPort();

                System.out.println("Cliente " + client + " -> Conectado");

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                String mensaje;

                while (!sc.isClosed()) {
                    mensaje = in.readUTF();
                    System.out.println(sc.getInetAddress().getHostName() + "::" + sc.getPort() + " = " + mensaje);
                    out.writeUTF("✓✓");

                    if (mensaje.toLowerCase().startsWith("x")) {
                        out.writeUTF(" -> Desconectado.");
                        sc.close();
                    }
                }

                System.out.println("Cliente " + client + " -> Desconectado");
            }
        } catch (SocketException soc) {
            System.out.println("El socket esta desconectado.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
