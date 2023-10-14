package exec;

import java.rmi.RemoteException;

import service.IStorage;

public class ExecMThread extends Thread{

    private IStorage storage;
    private int result;
    
    public ExecMThread(IStorage storage, int result) {
        
        this.storage = storage;
        this.result = result;

    }

    public void run(){

        System.out.println("[ExecM-Thread] Risultato dell'elaborazione: "+result);

        try {
            storage.store("Prodotto", result);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    
}
