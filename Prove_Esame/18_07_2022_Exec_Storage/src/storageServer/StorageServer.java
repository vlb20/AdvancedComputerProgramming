package storageServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IStorage;

public class StorageServer {
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IStorage storage = new ServerImpl();
            rmiRegistry.rebind("myStorage", storage);
            System.out.println("[StorageServer] Pronto!");
        
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
