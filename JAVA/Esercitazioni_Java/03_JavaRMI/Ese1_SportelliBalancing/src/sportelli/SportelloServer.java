package sportelli;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import servizi.IGestoreSportello;
import servizi.ISportello;

public class SportelloServer {
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IGestoreSportello gestore = (IGestoreSportello)rmiRegistry.lookup("mygestore");

            ISportello sportello = new SportelloImpl();
            gestore.sottoscrivi(sportello);

            System.out.println("[SportelloServer] Sottoscritto sportello al gestore");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

}
