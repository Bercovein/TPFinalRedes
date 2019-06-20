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

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println(yellow + "-->Servidor [IP:" + InetAddress.getLocalHost().getHostAddress() + "] Iniciado<--");
            System.out.println(cyan + "Ver Comandos: /help" + "\n");

            new ResponseThread();

            if (!isNull(serverSocket) && !serverSocket.isClosed()) {

                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();

                    String client = "[" + socket.getInetAddress().getHostName() + "->" + socket.getPort() + "]";
                    System.out.println("\n" + cyan + "<<Cliente " + client + " conectado>>" + "\n");

                    try {
                        new ServerThread(socket, client);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            }
        } catch (IllegalArgumentException e){
            System.out.println(red + "Rango de puerto no valido, Rango valido: 1024 a 65535");
        } catch(BindException ec){
            System.out.println(red + "Error: el puerto " + PORT + " ya está en uso");
        } catch(SocketException e) {
            if(!serverSocket.isClosed())
                System.out.println("\n" + red + "Acceso al servidor cerrado");
        } catch(IOException e){
            System.out.println(red + "Error en la ejecución del servidor");
        } finally {
            if(!isNull(serverSocket) && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(red + "Error al intentar cerrar el servidor");
                }
            }
            System.out.println("\n" + yellow + "-->Servidor Cerrado<--");
        }
    }
}
