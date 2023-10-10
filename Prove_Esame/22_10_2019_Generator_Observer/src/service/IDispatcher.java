package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{
    
    public void setReading(IReading reading) throws RemoteException;
    public void attach(IObserver o, String tipo) throws RemoteException;
    public int getReading() throws RemoteException;

}
