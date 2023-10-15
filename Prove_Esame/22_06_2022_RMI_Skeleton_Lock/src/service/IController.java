package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IController extends Remote{
    
    public boolean sensorRead(Reading reading) throws RemoteException;
    public void addActuator(int port) throws RemoteException;

}
