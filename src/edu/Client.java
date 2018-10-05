package edu;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 9000; // server port
        String address = "127.0.0.1"; // IP address

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // IP address object
            System.out.println("Connecting to the socket with IP address " + address + " and port " + serverPort);
            Socket socket = new Socket(ipAddress, serverPort); // socket for the IP Address and server port

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            System.out.println("To book a place type it and press ENTER. To finish type OK and press ENTER");
            System.out.println();

            while (true) {
                line = in.readUTF(); // waiting for the server request
                System.out.println("Available places to book : " + line);
                System.out.println();

                line = keyboard.readLine();
                System.out.println("Sending booking to the server...");
                out.writeUTF(line); // sending booking to the server
                out.flush(); // flushing the buffer

                if(line.toUpperCase().equals("OK"))
                    break;

                line = in.readUTF();
                System.out.println(line);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
