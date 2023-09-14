package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Interfaccia callback. Il client (main program in ObserverClient.java)
 * crea un oggetto di tipo Observer che sara' invocabile 'remotamente'
 * dal lato server.
 */

public interface Observer extends Remote{

    //callback method
    public void observerNotify() throws RemoteException;

}
