package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import service.MyService;
import service.Observer;

public class ServerImpl extends UnicastRemoteObject implements MyService{

    private Vector<Observer> attachedObservers; //lista degli observer registrati

    protected final static long serialVersionUID = 10;
    
    public ServerImpl() throws RemoteException {
        attachedObservers = new Vector<Observer>();
    }

    @Override
    public void attachObserver(Observer observer) throws RemoteException {
        
        System.out.println("\nNuovo observer registrato! \n"+observer.toString());
        attachedObservers.add(observer);

    }

    @Override
    public void service_method() throws RemoteException {
        
        System.out.println("service_method() invocato...Forza Napoli!");

        //notifica gli observer che un client ha invocato il metoo remoto
        notifyAllObservers();

    }

    /*
	 * Metodo privato per la notifica degli observer registrati.
	 * NOTA: gli Observer sono oggetti remoti
	 */
    private void notifyAllObservers() {

        System.out.println("(Notifico gli observer!)");
        int size = attachedObservers.size();

        for(int i=0; i<size; i++){

            try {
                attachedObservers.get(i).observerNotify();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }
    


}
