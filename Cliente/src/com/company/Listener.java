package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

import static com.company.Config.*;

public class Listener extends Thread {

    private BufferedReader in;
    private String message = "";


    Listener() {
        try {
            this.in = new BufferedReader(new InputStreamReader(Client.socket.getInputStream()));
            start();
        } catch (IOException e) {
            System.out.println(red + "Ups! No se pueden recibir los mensajes del servidor.");
        }
    }

    public void run(){

        try {
            while(message!=null){
                message = in.readLine();
                if(message!=null){
                    System.out.println(message);
                    System.out.print(cyan + "> ");
                }
            }
        } catch(SocketException ex ){
            System.out.println(red + "Se ha perdido la conexión con servidor");
        } catch (IOException e) {
            System.out.println(red + "Se ha perdido la conexión con el servidor");
        } finally {
            try {
                if(!Client.socket.isClosed())
                    Client.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(red + "Se ha desconectado del servidor.");
        }
    }
}
