package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IDispatcher;

public class Dispatcher {
    
    public static void main(String[] args) {
        
        try {
            
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = new DispatcherImpl();
            rmiRegistry.rebind("dispatcher", dispatcher);
            System.out.println("[DISPATCHER] Pronto!");

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
