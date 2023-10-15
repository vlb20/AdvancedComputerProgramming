package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import service.IActuator;

public class ActuatorProxy implements IActuator{
    
    private int port;
    
    public ActuatorProxy(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }

    @Override
    public boolean execute(String attributi) {
        
        boolean esito = false;

        try {
            Socket s = new Socket("127.0.0.1", port);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            dataOut.writeUTF(attributi);
            System.out.println("Invio la stringa Reading: "+attributi);
            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            esito = dataIn.readBoolean();
            s.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return esito;
    }


}
