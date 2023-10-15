package actuator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import service.IActuator;

public class ActuatorThread extends Thread{

    private Socket s;
    private IActuator act;

    public ActuatorThread(Socket s, IActuator act) {
        this.s = s;
        this.act = act;
    }

    public void run(){

        try {

            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            String attributes = dataIn.readUTF();
            Boolean esito = act.execute(attributes);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            dataOut.writeBoolean(esito);

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
