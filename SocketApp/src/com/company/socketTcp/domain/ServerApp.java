package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerApp {

    static String seeClients = "seeClients";

    public static void main(String[] args) {

        try {
            Server server = Server.getInstance(); //crea el servidor
            System.out.println("-->Servidor iniciado<--");

            Runnable thread = new ServerThread();
            thread.run();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                String message = console.readLine();
                if(message.equals(seeClients)){
                    System.out.println("<<Clients>>");
                    server.getClients().forEach(socket -> System.out.println(socket.getInetAddress()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
