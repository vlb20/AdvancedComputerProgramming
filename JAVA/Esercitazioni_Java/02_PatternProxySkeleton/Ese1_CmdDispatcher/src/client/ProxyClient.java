package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import service.IDispatcher;

public class ProxyClient implements IDispatcher{

    private String addr;
    private int port;

    public ProxyClient(String a, int p){

        addr = new String(a);
        port = p;
        
    }

    @Override
    public void sendCmd(int command) {

        try {
            
            Socket s = new Socket(addr, port);

            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            DataInputStream dataIn = new DataInputStream(s.getInputStream());

            dataOut.writeUTF("sendCmd");
            dataOut.writeInt(command);

            String res = dataIn.readUTF();

            if(res.compareTo("ack")==0){
                System.out.println("Ack ricevuto");
            }else{
                System.err.println("Errore, non ho ricevuto ack!");
            }

            s.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public int getCmd() {
        
        int x=0;

        try {
            
            Socket s = new Socket(addr, port);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            DataInputStream dataIn = new DataInputStream(s.getInputStream());

            dataOut.writeUTF("getCmd");
            x = dataIn.readInt();

            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return x;

    }
    
}
