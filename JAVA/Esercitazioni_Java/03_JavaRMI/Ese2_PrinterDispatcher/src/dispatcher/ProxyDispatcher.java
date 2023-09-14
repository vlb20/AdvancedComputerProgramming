package dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import services.IPrinter;

public class ProxyDispatcher implements IPrinter{

    private String host;
    private int port;

    public ProxyDispatcher(String host, int port){
        this.host=host;
        this.port=port;
    }

    @Override
    public boolean print(String docName) {
        
        boolean result = false;

        try {

            Socket s = new Socket(host, port);
            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());

            dataOut.writeUTF(docName);
            dataOut.flush();

            result = dataIn.readBoolean();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    


}
