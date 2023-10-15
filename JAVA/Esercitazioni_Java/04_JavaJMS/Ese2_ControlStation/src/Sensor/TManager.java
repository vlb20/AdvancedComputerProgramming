package Sensor;

import Coda.ICoda;

public class TManager extends Thread{

    private ICoda coda;
    private String mess;

    public TManager(ICoda coda, String mess) {
        this.coda = coda;
        this.mess = mess;
    }

    public void run(){
        coda.inserisci(mess);
        System.out.println("[TManager] Inserimento nella coda: "+mess);
    }
    
}
