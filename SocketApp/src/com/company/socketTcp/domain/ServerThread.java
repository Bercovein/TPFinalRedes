package com.company.socketTcp.domain;

import java.io.*;
import java.net.Socket;
import static com.company.socketTcp.domain.Config.*;
import static com.company.socketTcp.domain.ServerApp.clients;
import static com.company.socketTcp.domain.ServerApp.serverSocket;
import static java.util.Objects.isNull;

public class ServerThread extends Thread {

    private Socket socket;
    private String client;
    private BufferedReader in;
    private String mensaje;

    ServerThread(Socket socket, String client) throws IOException {

        this.client = client;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        clients.add(socket);
        start();
    }

    public void run() {
        try {
            while (!this.socket.isClosed() && !serverSocket.isClosed()) {
                this.mensaje = this.in.readLine(); // espero que el cliente me escriba

                    if(!isNull(mensaje)){
                        System.out.println(cyan + this.client + ": " + white + this.mensaje);
                        System.out.print(cyan + "> ");
                    }
                        if (this.mensaje.toLowerCase().equals("x")) { // sale del while cuando el cliente escribe una "x"
                            clients.remove(this.socket);
                            this.socket.close();
                        }
            }
            System.out.println(cyan + "<<Cliente: " + this.client + " -> Desconectado>>" + "\n");

        } catch (IOException | NullPointerException e) {
            System.out.println(red + "Se ha perdido la conexión con el cliente: " + client + "\n");


        } finally {
            try {
                if(!this.socket.isClosed()) {
                    clients.remove(this.socket);
                    this.socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
