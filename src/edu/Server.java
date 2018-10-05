package edu;

import java.net.*;
import java.io.*;
public class Server {

    public static BookablePlace[] places = new BookablePlace[]{new BookablePlace("Place #1"),
                                           new BookablePlace("Place #2"),
                                           new BookablePlace("Place lux"),};

    public static void main(String[] args)    {

        int port = 9000; // from 1025 to 65535
        try {
            ServerSocket ss = new ServerSocket(port); // socket linking to the port
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // waiting for a client
            System.out.println("Got a client");
            System.out.println();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String placeToBook = null;
            while(placeToBook == null || !placeToBook.toUpperCase().equals("OK")) {
                String availablePlacesLine = "";
                for(BookablePlace place : places){
                    if(!place.isBooked())
                        availablePlacesLine += place.getName() + " ";
                }
                out.writeUTF(availablePlacesLine);
                out.flush();
                System.out.println("Waiting for the next booking...");
                System.out.println();

                placeToBook = in.readUTF(); // waiting for the client to get info
                boolean successful = false;
                for(BookablePlace place : places){
                    if(place.getName().equals(placeToBook)) {
                        place.setBooked(true);
                        successful = true;
                    }
                }
                if(successful) {
                    System.out.println("Client booked : " + placeToBook);
                    out.writeUTF("Successful booking : " + placeToBook);
                }
                else{
                    System.out.println("Client entered wrong place : " + placeToBook);
                    out.writeUTF("Unrecognized place : " + placeToBook);
                }
                out.flush();
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}