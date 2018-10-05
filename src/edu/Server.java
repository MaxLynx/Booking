package edu;

import java.net.*;
import java.io.*;
public class Server {

    public static void main(String[] args)    {

        BookablePlace[] places = new BookablePlace[]{new BookablePlace("Place #1"),
                new BookablePlace("Place #2"),
                new BookablePlace("Place lux")};

        int port = 9000; // from 1025 to 65535
        try {
            ServerSocket ss = new ServerSocket(port); // socket linking to the port
            System.out.println("Waiting for clients...");

            while(true) {
                Socket socket = ss.accept(); // waiting for a client
                Runnable connectionHandler = new ConnectionHandler(socket, places);
                new Thread(connectionHandler).start();
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}