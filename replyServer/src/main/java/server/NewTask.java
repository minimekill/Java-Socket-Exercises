package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewTask extends Thread {

    private Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private BlockingQueue<String> messages;
    private ArrayList<NewTask> clients;
    private String name;

    public NewTask(Socket clientSocket, BlockingQueue<String> messages, ArrayList<NewTask> clients, String name) {
        this.clientSocket = clientSocket;
        this.messages = messages;
        this.clients = clients;
        this.name = name;
    }

    public String getNames() {
        return name;
    }

    public void setNames(String name) {
        this.name = name;
    }
    HashMap<String, String> translator = new HashMap();

    @Override
    public void run() {
        translateIni();
        try {
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            toClient.println("Welcome to Drayzins Server, press help#  to see what options you have");

            while (!clientSocket.isClosed()) {
                String message;
                while ((message = fromClient.readLine()) == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                String order = "";
                String word = "";

                for (int i = 0; i < message.length(); i++) {
                    if (message.substring(i, i + 1).equals("#")) {

                        order = message.substring(0, i);
                        word = message.substring(i + 1, message.length());
                    }
                }

                switch (order) {
                    case "msgto":
                        for (int i = 0; i < word.length(); i++) {
                            if (word.substring(i, i + 1).equals("@")) {

                                order = word.substring(0, i);
                                word = word.substring(i + 1, word.length());
                            }
                        }
                        NewTask client;
                        for (int i = 0; i < clients.size(); i++) {
                            client = clients.get(i);
                            if (client.getNames().equals(order)) {
                                client.getMessages(this.name + "(whisper) says: " + word);
                            }

                        }
                        break;
                    case "msgall":
                        msgAll(this.name + " (to all) says: " + word);
                        break;
                    case "help":
                        toClient.println("Here are the options for this chatserver");
                        toClient.println("to see the time type time#");
                        toClient.println("to see an echo of your message type echo#messagehere");
                        toClient.println("to see the reverse of a word type reverse#messagehere");
                        toClient.println("to see the lowercase or the uppercase of a word type lower#messagehere or uppercase#messagehere");
                        toClient.println("to se your online name use setname#yournamehere");
                        toClient.println("to see who is online type whoison#");
                        toClient.println("to message all who is on type msgtoall#messagehere");
                        toClient.println("to message a specific user type msgto#username@themessage");
                        toClient.println("to log off type exit#");
                        toClient.println("to turn off the server type exitserver#password (there is a password)");

                        break;
                    case "echo":
                        toClient.println(word);
                        break;
                    case "time":
                        Date date = new Date();
                        toClient.println(date);
                        break;
                    case "lowercase":
                        toClient.println(word.toLowerCase());
                        break;
                    case "reverse":
                        StringBuilder sb = new StringBuilder();
                        sb.append(word);
                        sb.reverse();
                        toClient.println(sb.toString());
                        break;
                    case "uppercase":
                        toClient.println(word.toUpperCase());
                        break;
                    case "translate":
                        toClient.println(translator.get(word));
                        break;
                    case "exit":
                        System.out.println("Goodbye");
                        clients.remove(this);
                        clientSocket.close();
                        break;
                    case "exitserver":
                        if (word.equals("popcorn")) {
                            toClient.println("closing server");
                            Server.killServer();

                        }else {
                            toClient.println("wrong password");
                        }
                        break;
                    case "setname":
                        this.name = word;
                        toClient.println("Your name is now: " + word);
                        break;
                    case "whoison":
                        for (int i = 0; i < clients.size(); i++) {
                            toClient.println(clients.get(i).getNames());

                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("IS CLOSED");
    }

    public void getMessages(String msg) {
        toClient.println(msg);
    }

    public void msgAll(String msg) {
        try {
            messages.put(msg);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void translateIni() {
        translator.put("dog", "hund");
        translator.put("cat", "dog");
        translator.put("house", "hus");
        translator.put("car", "bil");
        translator.put("bike", "cykel");
        translator.put("sun", "sol");
        translator.put("table", "bord");
    }
}
