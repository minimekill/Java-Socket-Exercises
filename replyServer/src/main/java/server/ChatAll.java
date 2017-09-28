package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatAll extends Thread {

    List<NewTask> clients = new ArrayList();
    BlockingQueue<String> messages;

    public ChatAll(List<NewTask> clients, BlockingQueue<String> messages) {
        this.clients = clients;
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                
                String temp = messages.take();
                for (int i = 0; i < clients.size(); i++) {
                    clients.get(i).getMessages(temp);
                    
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ChatAll.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
