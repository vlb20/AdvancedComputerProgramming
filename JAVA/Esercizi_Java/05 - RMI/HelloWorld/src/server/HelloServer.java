package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloServer {
    
    public static void main(String[] args) {
        
        try {

            HelloServerImplE obj_e = new HelloServerImplE();
            //HelloServerImplD obj_d = new HelloServerImplD();

            //Hello stub = (Hello) UnicastRemoteObject.exportObject(obj_d,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hello", obj_e);
            
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

}
