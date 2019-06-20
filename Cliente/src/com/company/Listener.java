package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

import static com.company.Config.*;
import static java.util.Objects.isNull;

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
            while(!isNull(this.message)){

                this.message = this.in.readLine(); // espera la respuesta del servidor

                if(!isNull(this.message)){

                    System.out.println(this.message);
                    System.out.print(cyan + "> ");
                }
            }

        } catch(SocketException ex ){
            System.out.println(red + "Se ha perdido la conexión con el servidor");
        } catch (IOException e) {
            System.out.println(red + "Se ha perdido la conexión con el servidor");
        }finally {
            try {
                if(!isNull(Client.socket) && !Client.socket.isClosed())
                    Client.socket.close();
            } catch (IOException e) {
                System.out.println(red + "Error al intentar desconectar el cliente del servidor");
            }
        }
    }
}
