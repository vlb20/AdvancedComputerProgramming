package rider;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import service.IRider;

public class RiderThread extends Thread{

    private Socket s;
    private IRider rider;

    public RiderThread(Socket s, IRider rider) {

        this.s = s;
        this.rider = rider;

    }
    
    public void run(){
        
        try {
        
            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            int id = dataIn.readInt();
            String address = dataIn.readUTF();
            rider.notifyOrder(id, address);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
