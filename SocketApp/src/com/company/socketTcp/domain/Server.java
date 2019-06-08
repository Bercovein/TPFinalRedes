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
    Server server;

    private Server(){
    }

    public Server getInstace() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }


}
