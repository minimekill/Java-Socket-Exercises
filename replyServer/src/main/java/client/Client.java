package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static Scanner scan = new Scanner(System.in);
    static String msg = "";

    public static void main(String[] args) {
        try {

            Socket clientSocket = new Socket("127.0.0.1", 47);
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ServerMsg serverMsg = new ServerMsg(fromServer);
            serverMsg.start();
            System.out.println("Enter a message");
            while (clientSocket.getInputStream().read() > -1 ) {
                msg = scan.next();
                toServer.println(msg);

                toServer.println("heya");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            toServer.close();
            fromServer.close();
            clientSocket.close();
             
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
