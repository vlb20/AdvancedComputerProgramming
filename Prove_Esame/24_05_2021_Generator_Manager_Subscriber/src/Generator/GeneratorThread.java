package Generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.AlertNotification;
import Service.IManager;

public class GeneratorThread extends Thread{
    
    public void run(){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("manager");
            AlertNotification an = new AlertNotification();
            manager.sendNotification(an);
            System.out.println("[GENERATOR-THREAD] invio Alert con componentID: "+an.getComponentID()+" e criticality: "+an.getCriticality());


        } catch (RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
