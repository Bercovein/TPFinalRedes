package com.company.socketTcp.domain;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private Socket socket;
    private String client;
    private BufferedReader in;
    private PrintWriter out;
    private String mensaje;

    public ServerThread(Socket socket, String client) throws IOException {
        this.socket = socket;
        this.client = client;

        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(
                        this.socket.getOutputStream())), true);
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

        }catch(SocketException e){
            System.out.println("Se ha perdido la conexión con el cliente" + client);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(!this.socket.isClosed())
                    this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    ServerThread() { }

    public void run() {
        try {
            while (true) {

                Server server = Server.getInstance(); //toma una instancia de la clase servidor

                Socket sc = server.getSocket().accept(); //espera a que algun cliente se conecte

                server.getClients().add(sc);

                //asigna address al cliente
                String client = sc.getInetAddress().getHostAddress();

                //printea que cliente se conectó
                System.out.println("Cliente [" + client + "] -> Conectado");

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                String mensaje;

                while (!sc.isClosed()) {
                    mensaje = in.readUTF();
                    System.out.println("[" + sc.getInetAddress().getHostAddress() + "]: " + mensaje);
                    out.writeUTF("✓✓");

                    if (mensaje.toLowerCase().startsWith("x")) {
                        server.getClients().remove(sc);
                        out.writeUTF(server.getServerName() + " -> Desconectado.");
                        sc.close();
                    }
                }

                System.out.println("Cliente " + client + " se ha desconectado de " + server.getServerName() + ".");
            }
        } catch (SocketException soc) {
            soc.printStackTrace();
            System.out.println("El socket esta desconectado.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
