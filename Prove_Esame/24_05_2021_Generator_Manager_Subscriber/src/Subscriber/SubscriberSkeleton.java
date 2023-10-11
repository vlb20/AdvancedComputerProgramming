package Subscriber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Service.ISubscriber;

public class SubscriberSkeleton implements ISubscriber{

    private int port;
    private ISubscriber sub; //per la delega

    public SubscriberSkeleton(ISubscriber sub, int port){
        
        this.sub=sub;
        this.port=port;

    }

    public void runSkeleton(){

        try {
            
            ServerSocket ss = new ServerSocket(port);
            Socket socket = null;
            while(true){
                socket = ss.accept();
                SubscriberThread t = new SubscriberThread(socket, this);
                t.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public void notifyAlert(int criticality) {
        sub.notifyAlert(criticality); //delego la reale implementazione
    }
    
}
