package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import coda.CodaImpl;
import coda.CodaWrapperLock;
import coda.ICoda;

public class Server {
    
    public static void main(String[] args) {
        
        ICoda coda = new CodaWrapperLock(new CodaImpl(5));

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IServer server = new ServerImpl(coda);
            rmiRegistry.rebind("myServer", server);
            System.out.println("Server pronto!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
