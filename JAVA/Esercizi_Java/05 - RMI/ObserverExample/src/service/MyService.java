package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * interface MyService, espone un metodo per la gestione della callback distribuita
 * e un metodo legato al servizio remoto vero e proprio
 */

public interface MyService extends Remote{
    
    //Metodo per consentire l'attach di un observer al subject
    public void attachObserver(Observer observer) throws RemoteException;

    //metodo legato al servizio -> stampa a video una string
    public void service_method() throws RemoteException;

}
