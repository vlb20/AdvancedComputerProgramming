package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import servizi.IGestoreSportello;

public class Client {
    
    public static void main(String[] args) {
        
        int T = 10;
        int R = 10;

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IGestoreSportello gestore = (IGestoreSportello) rmiRegistry.lookup("mygestore");
            
            for(int i=0; i<T; i++){
                ClientThread t = new ClientThread(R, gestore);
                t.start();
            }

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

}
