package com.company.socketTcp.domain;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import static com.company.socketTcp.domain.ServerApp.clients;

public class ResponseThread extends Thread {

    private PrintWriter out;

    ResponseThread(){
        start();
    }

    public void run(){
        try {
            while (true){
                Scanner teclado = new Scanner(System.in);
                String nombre = teclado.nextLine();
                clients.stream().forEach(socket ->
                {
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())), true);
                    } catch (IOException e) {
                        System.out.println("El mensaje no pudo ser enviado a [" +
                                socket.getInetAddress().getHostName() + "]");
                    }
                });
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
