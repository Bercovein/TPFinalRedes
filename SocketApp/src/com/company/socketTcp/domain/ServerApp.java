package com.company.socketTcp.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ServerApp {

    static final int PORT = 3000;
    static ServerSocket serverSocket;
    static List<Socket> clients = new ArrayList<Socket>();

    public static void main(String[] args) throws IOException {

        try{
            serverSocket = new ServerSocket(PORT);

            System.out.println("-->Servidor [IP:" + InetAddress.getLocalHost().getHostAddress() + "] Iniciado<--");

        }catch(BindException e){
            System.out.println("Error: el puerto " + PORT + " ya está en uso");
        }

        try {
            //new ResponseThread();
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {

                while (true) {
                    Socket socket = serverSocket.accept();
                    try {
                        new ServerThread(socket);
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
}
