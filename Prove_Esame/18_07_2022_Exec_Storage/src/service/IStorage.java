package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStorage extends Remote{
    
    public void store(String reqType, int result) throws RemoteException;
    
}
