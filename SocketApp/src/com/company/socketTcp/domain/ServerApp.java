package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerApp {


    public static void main(String[] args) {

        try {
            Server server = Server.getInstance(); //crea el servidor
            System.out.println("-->Servidor Iniciado<--");

            Runnable threadIn = new ServerThread();
            Runnable threadOut= new ResponseThread();
            threadIn.run();
            threadOut.run();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
