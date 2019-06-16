package com.company.socketTcp.domain;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


// https://www.youtube.com/watch?v=EnVrxz2iVSo


public class Server{

    static final int PORT = 3000;
    static List<Socket> clients = new ArrayList();
    static Server server;
    static ServerSocket serverSocket;
    private String serverName;

    private Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        serverName = "[SERVER:" + serverSocket.getInetAddress().getHostName() + "]";
    }

    public static Server getInstance() throws IOException {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public ServerSocket getSocket(){
        return serverSocket;
    }
    public String getServerName(){
        return serverName;
    }

    public List<Socket> getClients(){
        return clients;
    }

    public void responseAll(String message) throws IOException {
        if(clients!=null){
            for (int i=0; clients.size()>i; i++){
                DataOutputStream out = new DataOutputStream(clients.get(i).getOutputStream());
                out.writeUTF(message);
            }
        }
    }

    public void closeServer() throws IOException {

        if(clients!=null){
            while (clients.size()>0){
                clients.remove(0).close();
            }
        }
        serverSocket.close();
    }
}
