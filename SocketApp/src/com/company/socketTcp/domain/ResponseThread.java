package com.company.socketTcp.domain;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import static com.company.socketTcp.domain.ServerApp.clients;
import static com.company.socketTcp.domain.ServerApp.serverSocket;

public class ResponseThread extends Thread {

    private PrintWriter out;
    private boolean serverClosed;

    ResponseThread(){
        start();
    } //inicia el hilo

    public void run(){
        try {
            while (!serverClosed){
                Scanner console = new Scanner(System.in);
                String message = console.nextLine(); //levanta lo escrito en consola
                serverClosed = message.toLowerCase().equals("x");

                if(serverClosed){ //si se envia una "x" cerrará todos los sockets de clientes y luego el servidor
                    if(clients.size()>0){
                        do{
                            try {
                                clients.get(0).close();
                                clients.remove(clients.get(0));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }while(clients.size()>0);
                    }
                    serverSocket.close();
                }

                if(clients.size()>0 && !serverClosed) {

                    for (int i=0; clients.size()>i; i++){ //envia el mensaje a cada cliente

                        try {
                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(
                                            clients.get(i).getOutputStream())), true);
                            out.println("[Servidor]: " + message);
                        } catch (IOException e) {
                            clients.remove(clients.get(i)); //elimina al cliente de la lista
                            i--;
                        }
                    }
                    System.out.println("✓✓"); //marca que se enviaron a todos
                }
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
