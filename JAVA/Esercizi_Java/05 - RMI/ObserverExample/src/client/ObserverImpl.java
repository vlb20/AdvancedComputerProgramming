package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.Observer;

public class ObserverImpl extends UnicastRemoteObject implements Observer{

    protected ObserverImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    /*
	 * Implementazione del callbackMethod: il lato server notifica al client
	 * non appena il metodo service_method() verrà invocato da client di tipo InvokerClient
	 */
    @Override
    public void observerNotify() throws RemoteException {

        System.out.println("\n\nobserverNotify invocata dal ServerImpl...");

    }


    
}
