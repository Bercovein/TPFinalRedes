package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class ServerApp {

    public static void main(String[] args) {

        try {
            Server server = Server.getInstance(); //crea el servidor
            System.out.println("-->Servidor iniciado<--");

            Thread thread = new ServerThread();
            thread.start();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while(){
                console.readLine();
                //envia a todos
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
