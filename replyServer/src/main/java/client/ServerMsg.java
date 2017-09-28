package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMsg extends Thread {

    private BufferedReader fromServer;

    public ServerMsg(BufferedReader fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (true) {
                if ((msg = fromServer.readLine()) != null) {
                    System.out.println(msg);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
