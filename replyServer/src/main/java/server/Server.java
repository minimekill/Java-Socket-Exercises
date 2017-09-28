package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter toClient;
    BufferedReader fromClient;
    static ExecutorService ex = Executors.newFixedThreadPool(10);
    static ArrayList<String> chat = new ArrayList();
    private ArrayList<NewTask> clients = new ArrayList();
    private BlockingQueue<String> messages = new LinkedBlockingDeque();
    private NewTask nt;
    private ServerSocket socket;
    private ChatAll ca = new ChatAll(clients,messages);
    private int taskName = 0;
    public void serverStart() {
        ex.execute(ca);
        try {
            socket = new ServerSocket(47);
            while (true) {

                clientSocket = socket.accept();
                System.out.println("Connected");
                nt = new NewTask(clientSocket, messages,clients,Integer.toString(taskName));
                clients.add(nt);
                ex.execute(nt);
                taskName++;

            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 

    static void killServer() {
        ex.shutdownNow();
        System.exit(0);

    }
}
