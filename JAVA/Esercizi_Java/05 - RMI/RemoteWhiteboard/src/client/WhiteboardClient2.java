package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import whiteboard.Observer;
import whiteboard.Whiteboard;

//WhiteboardClient2 agisce da Server per il servizio remoto di callback
public class WhiteboardClient2 {
    
    public static void main(String[] args) {
        
        try {

            /*
			 * 1-Ottiene il riferimento alla lavagna remota.
			 * 2-Crea l'oggetto callback (ObserverImpl)
			 * 3-Invoca il metodo remoto di Witheboard per registrare l'observer
			 */
            Registry rmiRegistry = LocateRegistry.getRegistry();
            Whiteboard board = (Whiteboard)rmiRegistry.lookup("myWhiteboard");
            System.out.println("Ho ricevuto un riferimento < myWhiteboard >" + board.toString());

            /*
			 * il callback object viene creato lato client e poi verrà passato come parametro
			 * al metodo remoto esportato dal server, ovvero attachObserver.
			 * Quindi il client non dovrà fare una bind dell'observer
			 */

            Observer observer = new ObserverImpl(board);

            System.out.println("\nObserver con riferimento: \n" + observer.toString());

            board.attachObserver(observer);
        
        
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

}
