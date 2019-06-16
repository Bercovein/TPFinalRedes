package com.company.socketTcp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.util.Objects.isNull;

public class ServerApp {

    static final int PORT = 3000;
    static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {

        try{
            serverSocket = new ServerSocket(PORT);

            System.out.println("-->Servidor Iniciado<--");

        }catch(BindException e){
            System.out.println("Error, el puerto ya esta en uso");
        }

        try {
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {

                while (true) {
                    Socket socket = serverSocket.accept();
                    String client = "[" + socket.getInetAddress().getHostName() + "::" + socket.getPort() + "]";

                    System.out.println("<<Cliente" + client + " -> Conectado>>");

                    try {
                        new ServerThread(socket, client);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            }
        } finally {
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("-->Servidor Cerrado<--");
            }
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
