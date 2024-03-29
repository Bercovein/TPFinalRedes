package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.Config.*;
import static java.util.Objects.isNull;

public class Client {

    private static String host;
    private static int port;
    public static Socket socket;

        public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        connectToServer(sc);  //Creo el socket para conectarme con el servidor

        try {
            if (!isNull(socket) && !socket.isClosed()) {

                new Listener();

                String mensaje;
                boolean exit;

                System.out.println( yellow + "Conexión establecida con: " + host + " por el puerto: " + port);

                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                do {
                    System.out.print(cyan + "> ");
                    mensaje = console.readLine(); // escribimos el mensaje y lo guardamos

                    exit = mensaje.toLowerCase().equals("x");

                    if(!isNull(socket) && !socket.isClosed()) {

                        out.println(mensaje); // para enviar el mensaje al servidor
                        System.out.println(green + "✓✓");
                    }
                } while (!exit && !isNull(socket) && !socket.isClosed());

                System.out.println(yellow + "Desconectado del servidor.");
            }
        } catch (ConnectException con){
            System.out.println(red + "No se pudo acceder al servidor");
        } catch (SocketException soc){
            System.out.println(red + "3Se ha perdido la conexión con el servidor.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void connectToServer(Scanner sc){

        try {
            System.out.println(cyan + "Ingrese la dirección host del servidor");
            host = sc.nextLine();

            System.out.println(cyan + "Ingrese el puerto del servidor");
            port = verifyIsInt(sc);

            System.out.println(yellow + "(Buscando servidor...)");
            socket = new Socket(host,port);

        }catch(UnknownHostException e) {
            System.out.println(red + "El host ingresado es desconocido, intente nuevamente");
            connectToServer(new Scanner(System.in));
        }catch(IllegalArgumentException e){
            System.out.println(red + "Rango de puerto no valido, Rango valido: 1024 a 65535");
        } catch (ConnectException con){
            System.out.println(red + "No se pudo acceder al servidor");
        } catch (IOException e) {
            System.out.println(red + "Error al intentar conectar cliente al servidor");
        }
    }

    public static int verifyIsInt(Scanner sc){

        int number;

        while(!sc.hasNextInt()){

            System.out.println(red + "Solo se puede ingresar numeros entre 1024 y 65535, intente nuevamente");
            sc.next();
        }
        number = sc.nextInt();

        if(number < 1024 || number > 65535){
            System.out.println(red + "Solo se puede ingresar numeros entre 1024 y 65535, intente nuevamente");
            number = verifyIsInt(sc);
        }
        return number;
    }
}