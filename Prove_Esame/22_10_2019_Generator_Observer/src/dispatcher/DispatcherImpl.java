package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import service.IDispatcher;
import service.IObserver;
import service.IReading;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    private Vector<IObserver> observersT;
    private Vector<IObserver> observersP;
    private IReading r;

    public DispatcherImpl() throws RemoteException {
        observersT = new Vector<IObserver>();
        observersP = new Vector<IObserver>();
    }

    @Override
    public synchronized void setReading(IReading reading) throws RemoteException {
        this.r = reading;
        System.out.println("[DISPATCHER] setReading per "+r.getTipo()+": "+r.getValore());
        try {
            Thread.sleep(1000*((int)Math.random()*5)+1);
            if(r.getTipo().equals("temperatura")){
                for(int i=0; i<observersT.size(); i++){
                    observersT.get(i).notifyReading();
                }
            }else{
                for(int i=0; i<observersP.size(); i++){
                    observersP.get(i).notifyReading();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attach(IObserver o, String tipo) throws RemoteException {
        
        System.out.println("[DISPATCHER] Richiesta di attach per l'observer di tipo: "+tipo);
        if(tipo.equals("temperatura")){
            observersT.add(o);
        }else{
            observersT.add(o);
        }
    
    }

    @Override
    public int getReading() throws RemoteException {
        System.out.println("[DISPATCHER] getReading ha letto il valore:" +r.getValore());
        return r.getValore();
    }


    
}
