package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.MyService;

public class Server {

    public static void main(String[] args) {
        
        /*
         * Creazione e registrazione dell'oggetto remoto
         */

        try {

            System.out.println("Creando l'oggetto...");
            MyService myservice = new ServerImpl();

            System.out.println(myservice.toString() + "\n");

            Registry rmiRegistry = LocateRegistry.getRegistry();
            rmiRegistry.rebind("myservice", myservice);
            System.out.println("Oggetto registrato con nome < myservice >\n");

        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }
    
}
