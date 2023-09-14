package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.MyService;
import service.Observer;

public class ObserverClient {
    
    public static void main(String[] args) {
        
        try {
            
            /*
			 * 1-Ottiene il riferimento al servizio remoto
			 * 2-Crea l'oggetto callback (ObserverImpl)
			 * 3-Invoca il metodo remoto di MyService per registrare l'observer (attachObserver)
			 */

            Registry rmiRegistry = LocateRegistry.getRegistry();
            MyService stub_myservice = (MyService)rmiRegistry.lookup("myservice");
            System.out.println("Ho ricevuto il riferimento a <myservice>");
            System.out.println(stub_myservice.toString());
            
            /*
			 * il callback object viene creato lato client e poi verrà passato come parametro
			 * al metodo remoto esportato dal server, ovvero attachObserver
			 */

            Observer observer = new ObserverImpl();

            System.out.println("\nObserver con riferimento: "+observer.toString());

            stub_myservice.attachObserver(observer);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

}
