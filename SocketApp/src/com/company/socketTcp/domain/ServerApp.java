package com.company.socketTcp.domain;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.socketTcp.domain.Config.*;
import static java.util.Objects.isNull;

public class ServerApp {

    static ServerSocket serverSocket;
    static List<Socket> clients = new ArrayList<Socket>();

    public static void main(String[] args) throws IOException {

        try{
            serverSocket = new ServerSocket(PORT);

            System.out.println(yellow + "-->Servidor [IP:" + InetAddress.getLocalHost().getHostAddress() + "] Iniciado<--");
            System.out.println(cyan + "Ver Comandos: /help");

        }catch(BindException e){
            System.out.println(red + "Error: el puerto " + PORT + " ya está en uso");
        }

        try {
            new ResponseThread();
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {

                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    String client = "[" + socket.getInetAddress().getHostName() + "->" + socket.getPort() + "]";
                    System.out.println(cyan + "<<Cliente "+ client + "conectado>>");

                    try {
                        new ServerThread(socket,client);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            }
        }catch(SocketException e){
            System.out.println(red + "Acceso al servidor cerrado");
        }
        finally {
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println(yellow + "-->Servidor Cerrado<--");

        }
    }
}
