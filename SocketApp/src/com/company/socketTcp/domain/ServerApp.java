package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    static final int PORT = 3000;

    public static void main(String[] args) throws IOException {

        ServerSocket s = new ServerSocket(PORT);
        System.out.println("-->Servidor Iniciado<--");
        try {
            while(true) {
                Socket socket = s.accept();
                try {
                    new ServerThread(socket);
                } catch(IOException e) {
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }



    /*
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
    }*/
}
