package com.company.socketTcp.domain;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.company.socketTcp.domain.Config.*;
import static com.company.socketTcp.domain.ServerApp.clients;
import static com.company.socketTcp.domain.ServerApp.serverSocket;
import static java.util.Objects.isNull;

public class ResponseThread extends Thread {

    private PrintWriter out;
    private boolean serverClosed;

    ResponseThread(){
        start();
    } //inicia el hilo

    public void run(){
        try {
            Scanner console = new Scanner(System.in);
            while (!serverClosed){

                System.out.print(cyan + "> ");
                String message = console.nextLine(); //levanta lo escrito en consola
                serverClosed = message.toLowerCase().equals("x");

                switch (message){
                    case "x":
                        closeServer(); //cierra el servidor
                        break;
                    case "/whisp":
                        whisperTo(); //hablar con un cliente en especifico
                        break;
                    case "/help":
                        help();
                        break;
                    case "/clients":
                        getClients();
                        break;
                    default:
                        responseAll(message); //responde a todos
                        break;
                }
                sleep(100);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() throws IOException {
        if(serverClosed){ //si se envia una "x" cerrará todos los sockets de clientes y luego el servidor
            if(clients.size()>0){

                for(int i = 0; i < clients.size(); i++){
                    clients.remove(clients.get(i));
                    clients.get(i).close();
                }
            }
            serverSocket.close();
        }
    }

    public void responseAll(String message){
        if(clients.size()>0 && !serverClosed) {

            for (int i=0; clients.size()>i; i++){ //envia el mensaje a cada cliente

                try {
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(
                                    clients.get(i).getOutputStream())), true);
                    out.println(yellow + "[Servidor]: " + white + message);
                } catch (IOException e) {
                    clients.remove(clients.get(i)); //elimina al cliente de la lista
                    i--;
                }
            }
            System.out.println(green + "✓✓"); //marca que se enviaron a todos
        }else
            System.out.println(yellow + "-- El servidor esta vacío, nadie lo recibirá --" + "\n");
    }

    public void whisperTo() throws IOException { //chat para un cliente en especifico

        boolean command = false;
        List<Socket> sc = null;
        String client = "";

        Scanner console = new Scanner(System.in);

        while (!serverClosed && !command){

            if(isNull(sc)){

                System.out.println("\n" + pink + "Escriba la ip del cliente:");
                System.out.print(cyan + "> ");
                String message = console.nextLine();
                sc = clients.stream()
                        .filter(socket -> socket.getInetAddress().getHostName().equals(message))
                        .collect(Collectors.toList());
                if(sc.size()>1) {
                    System.out.println(pink + "Escriba el puerto del cliente:");
                    System.out.print(cyan + "> ");
                    String message2 = console.nextLine();
                    sc = sc.stream().filter(socket -> socket.getPort() == Integer.parseInt(message2)).collect(Collectors.toList());
                }
                if(!isNull(sc) && sc.size()>0) {
                    client = sc.get(0).getInetAddress().getHostName() + ":" + sc.get(0).getPort();
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(
                                    sc.get(0).getOutputStream())), true);
                    System.out.println("\n" + pink + "Susurrando a: <" + client + ">");
                }else {
                    System.out.println(red + "No se encuentra el cliente, volverá al chat principal" + "\n");
                    break;
                }
            }
            System.out.print(pink + "> ");
            String message = console.nextLine(); //levanta lo escrito en consola
            command = message.toLowerCase().equals("/back");

            switch (message){
                case "/back":
                    System.out.println(yellow + "(Regresó al chat principal)" + "\n");
                    command = true;
                    break;
                case "/whisp": //en caso de querer cambiar de cliente para hablar
                    sc = null;
                    break;
                default:
                    out.println(pink + "[Servidor]: " + message);
                    break;
            }
        }
    }

    private void help(){
        System.out.println("\n" + yellow + "COMANDOS: ");
        System.out.println(yellow +"/help    -> Ver comandos de navegación");
        System.out.println(yellow +"/whisp   -> Hablar a cliente por privado");
        System.out.println(yellow +"/back    -> Volver al chat general");
        System.out.println(yellow +"/clients -> Listar los clientes conectados");
        System.out.println(yellow +"x        -> Cerrar el servidor" + "\n");
    }

    private void getClients(){

        if (clients.size() > 0) {

            System.out.println(yellow + "<<<Clientes conectados>>>");
            clients.forEach(socket ->
                    System.out.println(yellow + "[" + socket.getInetAddress().getHostName()
                            + "::" + socket.getPort() + "]"));
        } else
            System.out.println(yellow + "-- No hay clientes conectados, servidor Vacío --");

    }
}
