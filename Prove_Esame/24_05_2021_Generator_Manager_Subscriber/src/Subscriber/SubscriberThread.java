package Subscriber;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import Service.ISubscriber;

public class SubscriberThread extends Thread{

    private Socket socket;
    private ISubscriber sub;

    public SubscriberThread(Socket socket, ISubscriber sub) {
        this.socket = socket;
        this.sub = sub;
    }

    public void run(){
        
        try {
            DataInputStream istream = new DataInputStream(socket.getInputStream());
            int criticality = istream.readInt();
            sub.notifyAlert(criticality);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
