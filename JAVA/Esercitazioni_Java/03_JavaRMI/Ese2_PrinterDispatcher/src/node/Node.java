package node;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;

public class Node {
    
    public static void main(String[] args) {
        
        Registry rmiRegistry;
        try {

            rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher stub = (IDispatcher) rmiRegistry.lookup("dispatcher");
        
            for(int i=0; i<5; i++){
            
                WorkerNode t = new WorkerNode(stub);
                t.start();

            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        

    }

}
