package rider;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IManager;
import service.IRider;

public class Rider {
    
    public static void main(String[] args) {

        if(args.length != 3){
            System.out.println("Inserisci i parametri [location - port number - filename]");
            return;
        }
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("myManager");
            manager.subscriber(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("[Rider] Sottoscrizione al Manager effettuata!");

            IRider rider = new RiderImpl(args[2]);
            RiderSkeleton skeleton = new RiderSkeleton(rider, Integer.parseInt(args[1]));
            skeleton.runSkeleton();


        } catch (RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
