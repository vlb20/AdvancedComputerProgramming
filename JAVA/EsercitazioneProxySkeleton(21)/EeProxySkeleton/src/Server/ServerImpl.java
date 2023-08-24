package Server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerImpl extends ServerSkeleton {

    private int [] commands;
    private int testa;
    private int coda;
    private int occ;
    private int size;

    private Lock l;
    private Condition spa_disp;
    private Condition mes_disp;

    public ServerImpl(){
        commands = new int[5];
        testa = 0;
        coda = 0;
        occ = 0;
        size = 5;

        l = new ReentrantLock();
        spa_disp = l.newCondition();
        mes_disp = l.newCondition();
    }

    private int getSize(){
        return occ;
    }

    private boolean full(){
        if(occ==5){
            return true;
        }
        return false;
    }

    private boolean empty(){
        if(occ==0){
            return true;
        }
        return false;
    }

    @Override
    public int getCmd() {
        
        //devi gestire la gestione dei comandi nel vettore 
        //in maniera mutuamente esclusiva
        //con monitor signal and continue

        //Consumazione!!
        l.lock();

        int x = 0;
        try {
            //quando vuota non posso prelevare, mi fermo
            while(this.empty()){
                System.out.println("Non posso prelevare, coda vuota!");
                mes_disp.await();
            }

            x = commands[testa%size];

            occ = occ-1;
            testa=testa+1;

            spa_disp.signal(); //notifico

        } catch (InterruptedException e) {
            System.err.println("Eccezione di interruzione nel consumatore!");
        
        }finally{
            l.unlock();
        }

        return x;
    }

    @Override
    public void sendCmd(Integer s) {
        
        l.lock();
        try {
            //quando il vettore è pieno non posso aggiungere, mi fermo
            while(this.full()){
                System.out.println("Non posso creare, coda piena!");
                spa_disp.await();
            }

            commands[coda%size]=s;
            System.out.println("[Send] Ho aggiunto!");

            occ=occ+1;
            coda=coda+1;

            mes_disp.signal(); //notifico

        } catch (InterruptedException e) {
            System.err.println("Eccezione di interruzione nel produttore!");
        
        }finally{
            l.unlock();
        }

    }

    
    
    
}
