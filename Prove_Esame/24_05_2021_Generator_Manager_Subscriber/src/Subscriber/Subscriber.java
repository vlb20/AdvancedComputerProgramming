package Subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IManager;
import Service.ISubscriber;

public class Subscriber {
    
    public static void main(String[] args) {

        if(args.length != 3){
            System.out.println("Devi passare[ componentID - port number - filename ]");
            return;
        }
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("manager");
            manager.subscribe(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("[SUBSCRIBER] Sottoscrizione al manager");

            ISubscriber sub = new SubscriberImpl(args[2]);
            SubscriberSkeleton skeleton = new SubscriberSkeleton(sub, Integer.parseInt(args[1]));
            skeleton.runSkeleton();

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
