package rider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import service.IRider;

public class RiderSkeleton implements IRider{

    private IRider rider;
    private int port;

    public RiderSkeleton(IRider rider, int port){
        this.rider = rider;
        this.port = port;
    }

    public void runSkeleton(){

        try {

            ServerSocket ss = new ServerSocket(port);
            Socket s = null;
            while(true){
                s = ss.accept();
                RiderThread t = new RiderThread(s, rider);
                t.start();
            }
            

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public void notifyOrder(int id, String address) {

        rider.notifyOrder(id, address); //delego

    }
    
}
