package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IManager;
import service.Order;

public class GeneratorThread extends Thread{
    
    public void run(){

        Order order = new Order();

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("myManager");
            manager.setOrder(order);
            System.out.println("[GENERATOR-THREAD] Ordine settato con id: "+order.getId()+" e address: "+order.getAddress());
            
        } catch (RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
