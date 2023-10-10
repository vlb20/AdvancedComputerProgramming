package observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import service.IDispatcher;
import service.IObserver;

public class ObserverImpl extends UnicastRemoteObject implements IObserver{

    private String filename;

    public ObserverImpl(String filename) throws RemoteException{
        this.filename = filename;
    }

    @Override
    public void notifyReading() throws RemoteException {
        
        System.out.println("[OBSERVER] è stata invocata la notifyReading()");

        Registry rmiRegistry = LocateRegistry.getRegistry();
        IDispatcher dispatcher;
        try {
        
            dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
            int valore_letto = dispatcher.getReading();
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(String.valueOf(valore_letto)+"\n");
            pw.close();

        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
    
}
