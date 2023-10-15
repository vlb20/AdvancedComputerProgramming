package sensorsManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IController;
import service.Reading;

public class SensorT extends Thread{
    
    public void run(){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IController controller = (IController) rmiRegistry.lookup("myController");
            Reading reading = new Reading();
            controller.sensorRead(reading);
            System.out.println("Passo la Reading con type: "+reading.getType()+" e data: "+reading.getData());
        
        } catch (RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
