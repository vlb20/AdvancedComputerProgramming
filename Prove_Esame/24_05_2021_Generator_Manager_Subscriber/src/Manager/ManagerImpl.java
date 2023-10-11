package Manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import Service.AlertNotification;
import Service.IManager;

public class ManagerImpl extends UnicastRemoteObject implements IManager{

    private Vector<SubscriberProxy> subs;

    public ManagerImpl() throws RemoteException {
        super();
        subs = new Vector<SubscriberProxy>();
    }

    @Override
    public synchronized void sendNotification(AlertNotification an) throws RemoteException {
        System.out.println("[MANAGER] AlertNotification ricevuta...invio notifica");
        for(int i=0; i<subs.size(); i++){
            if(subs.get(i).getComponentID() == an.getComponentID()){
                subs.get(i).notifyAlert(an.getCriticality());
            }
        }
    }

    @Override
    public void subscribe(int componentID, int port) throws RemoteException {
        System.out.println("[MANAGER] Richiesta di sottoscrizione ricevuta...");
        SubscriberProxy sub = new SubscriberProxy(componentID, port);
        subs.add(sub);
    }
    
}
