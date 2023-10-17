package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManager extends Remote{
    
    public int setOrder(Order ord) throws RemoteException;
    public void subscriber(int location, int port) throws RemoteException;

}
