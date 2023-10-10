package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IDispatcher;
import service.Reading;

public class GeneratorThread extends Thread{
    
    private IDispatcher dispatcher;

    public GeneratorThread(){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
        
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

    public void run(){

        for(int i=0; i<3; i++){

            try {
                
                Reading r = new Reading();
                Thread.sleep(5000);
                dispatcher.setReading(r);
                System.out.println("[GENERATOR] setReading con tipo: "+r.getTipo()+" e valore: "+r.getValore());
            
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
