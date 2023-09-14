package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;

public class DispatcherServer {
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = new DispatcherImpl();
            rmiRegistry.rebind("dispatcher", dispatcher);
            System.out.println("[DISPATCHER] Rebind effettuato...");

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
