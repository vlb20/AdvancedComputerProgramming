package manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import service.IRider;

public class RiderProxy implements IRider{

    private int location;
    private int port;

    public RiderProxy(int location, int port){

        this.location = location;
        this.port = port;

    }

    public int getLocation(){
        return this.location;
    }

    @Override
    public void notifyOrder(int id, String address) {

        try {

            Socket s = new Socket("127.0.0.1", port);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            dataOut.writeInt(id);
            dataOut.writeUTF(address);
            s.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
