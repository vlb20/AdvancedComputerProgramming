package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManager extends Remote{
    
    void sendNotification(AlertNotification an) throws RemoteException;
    void subscribe(int componentID, int port) throws RemoteException;

}
