package gestore;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import servizi.IGestoreSportello;

public class GestoreServer {
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IGestoreSportello gestore = new GestoreSportelloImpl();
            rmiRegistry.rebind("mygestore", gestore);

            System.out.println("[GestoreServer] Gestore avviato...");

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
