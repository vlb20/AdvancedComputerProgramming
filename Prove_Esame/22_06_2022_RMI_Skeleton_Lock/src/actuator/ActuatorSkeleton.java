package actuator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import service.IActuator;

public abstract class ActuatorSkeleton implements IActuator{
    
    private int port;

    public ActuatorSkeleton(int port){

        this.port = port;

    }

    public void runSkeleton(){

        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = null;
            while(true){
                s = ss.accept();
                ActuatorThread t = new ActuatorThread(s, this);
                t.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
