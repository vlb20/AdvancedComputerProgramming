package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import whiteboard.Observer;
import whiteboard.Shape;
import whiteboard.Whiteboard;

public class ObserverImpl extends UnicastRemoteObject implements Observer{

    private static final long serialVersionUID = 1L;

    private Whiteboard remoteWhiteboard;//riferimento alla lavagna remota

    public ObserverImpl(Whiteboard w) throws RemoteException {
        remoteWhiteboard = w;
    }

    /*
	 * Implementazione del callbackMethod: il lato server notifica al client
	 * che e' stata aggiunta una nuova forma geometrica sulla lavagna;
	 * il client richiede lo stato corrente della lavagna al server
	 */

    @Override
    public void observerNotify() throws RemoteException {

        System.out.println("\n\nNotified! Contenuti della lavagna correnti: ");

        //invocazione remota sul subject (Whiteboard)
        Vector<Shape> v = remoteWhiteboard.getShapes();

        int size = v.size();

        for(int i=0; i<size; i++){
            ((Shape)v.get(i)).draw();
        }

    }
    
}
