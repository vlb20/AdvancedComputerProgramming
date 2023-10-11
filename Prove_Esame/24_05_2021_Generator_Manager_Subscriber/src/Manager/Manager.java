package Manager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IManager;

public class Manager{
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = new ManagerImpl();
            rmiRegistry.rebind("manager", manager);
            System.out.println("[MANAGER] Rebind effettuato!");

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
