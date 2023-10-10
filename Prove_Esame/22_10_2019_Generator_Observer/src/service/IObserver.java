package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote{
    
    public void notifyReading() throws RemoteException;

}
