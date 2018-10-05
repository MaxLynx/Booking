package edu;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private Socket socket;
    private BookablePlace[] places;

    public ConnectionHandler(Socket socket, BookablePlace[] places){
        this.socket = socket;
        this.places = places;
    }

    public void run(){
        try {
            System.out.println("Got a client");
            System.out.println();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String placeToBook = null;
            while (placeToBook == null || !placeToBook.toUpperCase().equals("OK")) {
                String availablePlacesLine = "";
                for (BookablePlace place : places) {
                    if (!place.isBooked())
                        availablePlacesLine += place.getName() + " ";
                }
                out.writeUTF(availablePlacesLine);
                out.flush();
                System.out.println("Waiting for the next booking...");
                System.out.println();

                placeToBook = in.readUTF(); // waiting for the client to get info
                boolean successful = false;
                for (BookablePlace place : places) {
                    if (place.getName().equals(placeToBook) && !place.isBooked()) {
                        place.setBooked(true);
                        successful = true;
                    }
                }
                if (successful) {
                    System.out.println("Client booked : " + placeToBook);
                    out.writeUTF("Successful booking : " + placeToBook);
                } else {
                    System.out.println("Requested already booked or unavailable place : " + placeToBook);
                    out.writeUTF("Unavailable or already booked place : " + placeToBook);
                }
                out.flush();
            }
        }
        catch(Exception x) { x.printStackTrace(); }
    }
}
