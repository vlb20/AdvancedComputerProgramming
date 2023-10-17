package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import service.IManager;
import service.Order;

public class ManagerImpl extends UnicastRemoteObject implements IManager {

    private Vector<RiderProxy> riders;

    public ManagerImpl() throws RemoteException {

        riders = new Vector<RiderProxy>();
    
    }

    @Override
    public synchronized int setOrder(Order ord) throws RemoteException {

        int esito = -1;

        int location = ord.getLocation();
        
        for(int i=0; i<riders.size(); i++){
            if(riders.get(i).getLocation() == location){
                riders.get(i).notifyOrder(ord.getId(), ord.getAddress());
                esito = 1;
            }
        }

        return esito;

    }

    @Override
    public void subscriber(int location, int port) throws RemoteException {

        RiderProxy rider = new RiderProxy(location, port);
        riders.add(rider);
        System.out.println("[MANAGER] Ricevuta richiesta di sottoscrizione!");

    }
    
}
