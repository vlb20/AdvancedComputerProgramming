package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import servizi.IMagazzino;

public class ProxyClient implements IMagazzino{

    @Override
    public void deposita(String tipo, int id) {
        
        try {
            
            Socket s = new Socket("127.0.0.1", 8000);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            dataOut.writeUTF("deposita");
            dataOut.writeUTF(tipo);
            dataOut.writeInt(id);
            dataOut.flush();

            System.out.println("[PROXY] Invio richiesta di deposito dell'"+tipo+" con id: "+id);

            s.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public int preleva(String articolo) {
        
        int val = -1;

        try {
            
            Socket s = new Socket("127.0.0.1",8000);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            dataOut.writeUTF("preleva");
            dataOut.writeUTF(articolo);
            
            val = dataIn.readInt();

            System.out.println("[PROXY] Prelevo "+articolo+" con id: "+val);

            s.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return val;

    }
    

}
