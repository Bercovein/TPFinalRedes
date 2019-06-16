package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResponseThread implements Runnable {

    static final String SeeClients = "SeeClients";
    static final String ServerClose = "ServerClose";

    public void run(){

        try {
            Server server = Server.getInstance();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while (!server.getSocket().isClosed()) {
                String message = console.readLine();
                switch (message) {
                    case SeeClients:
                        System.out.println("<<Clients>>");
                        server.getClients().forEach(socket -> System.out.println(socket.getInetAddress()));
                        break;
                    case ServerClose:
                        server.closeServer();
                        System.out.println("-->Servidor Cerrado<--");
                        break;
                    default:
                        server.responseAll(message);
                        break;
                }
            }
            System.out.println("Bye Bye");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
