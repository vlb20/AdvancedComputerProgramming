package observer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IDispatcher;
import service.IObserver;

public class Observer {
    
    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("Inserisci il TIPO di interesse e il NOME del FILE");
            return;
        }
        
        try {

            Registry rmRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) rmRegistry.lookup("dispatcher");
            IObserver observer = new ObserverImpl(args[1]); //passo il nome del file
            dispatcher.attach(observer, args[0]);
            System.out.println("[OBSERVER] Attach effettuata!");

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
