package com.company.socketTcp.domain;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static com.company.socketTcp.domain.ServerApp.clients;

public class ServerThread extends Thread {

    private Socket socket;
    private String client;
    private BufferedReader in;
    private PrintWriter out;
    private String mensaje;

    ServerThread(Socket socket) throws IOException {

        this.client = "[" + socket.getInetAddress().getHostName() + "::" + socket.getPort() + "]";
        System.out.println("<<Cliente" + client + " -> Conectado>>");
        this.socket = socket;

        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(
                        this.socket.getOutputStream())), true);
        clients.add(socket);
        start();
    }

    public void run() {
        try {
            while (!this.socket.isClosed()) {
                this.mensaje = this.in.readLine(); // espero que el cliente me escriba

                System.out.println("Cliente" + this.client + " -> " + this.mensaje);

                this.out.println("✓✓ -> " + this.mensaje); // ack, envia confirmacion al cliente.

                if (this.mensaje.toLowerCase().equals("x")) { // sale del while cuando el cliente escribe una "x"
                    this.out.println("-> Desconectado del Servidor");
                    this.socket.close();
                }
            }
            System.out.println("<<Cliente: " + this.client + " -> Desconectado>>");

        }catch(SocketException ex ){
            System.out.println("Se ha perdido la conexión con el cliente: " + client);
        } catch (IOException e) {
            System.out.println("Se ha perdido la conexión con el cliente: " + client);

        } finally {
            try {
                if(!this.socket.isClosed())
                    this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
