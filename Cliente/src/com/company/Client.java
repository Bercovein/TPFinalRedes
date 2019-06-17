package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.Objects.isNull;

public class Client {

    private static String host;
    private static int port;
    public static Socket socket = null;

        public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        connectToServer(sc);  //Creo el socket para conectarme con el servidor

        try {
            if (!isNull(socket) && socket.isConnected()) {
                //new Listener();
                String ack; //Recibo el mensaje del servidor
                String mensaje;
                boolean exit;

                System.out.println("Conexión establecida con: " + host + " por el puerto: " + port);

                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                do {
                    mensaje = console.readLine(); // escribimos el mensaje y lo guardamos
                    out.println(mensaje); // para enviar el mensaje al servidor

                    ack = in.readLine(); // el servidor me envia la confirmacion de mi mensaje
                    System.out.println(ack);

                    exit = mensaje.toLowerCase().startsWith("x");

                    if (exit) {
                        ack = in.readLine();
                        System.out.println(ack);
                    }
                } while (!exit);

                in.close();
                out.close();
                socket.close();
            }

        } catch (ConnectException con){
            System.out.println("No se pudo acceder al servidor");
        } catch (SocketException soc){
            System.out.println("Se ha perdido la conexión con el servidor.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void connectToServer(Scanner sc){

        try {
            System.out.println("Ingrese la dirección host del servidor");
            host = sc.nextLine();

            System.out.println("Ingrese el puerto del servidor");
            port = verifyIsInt(sc);

            socket = new Socket(host,port);

        }catch(UnknownHostException e) {
            System.out.println("El host ingresado es desconocido, intente nuevamente");
            connectToServer(new Scanner(System.in));
        }catch(IllegalArgumentException e){
            System.out.println("Rango de puerto no valido, Rango valido: 0 a 65535");
        } catch (ConnectException con){
            System.out.println("No se pudo acceder al servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int verifyIsInt(Scanner sc){

        int number;

        while(!sc.hasNextInt()){

            System.out.println("Solo se puede ingresar numeros entre 0 y 65535, intente nuevamente");
            sc.next();
        }
        number = sc.nextInt();

        if(number < 0 || number > 65535){
            System.out.println("Solo se puede ingresar numeros entre 0 y 65535, intente nuevamente");
            number = verifyIsInt(sc);
        }
        return number;
    }
}