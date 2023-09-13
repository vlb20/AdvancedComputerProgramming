package server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DispatcherImpl extends DispatcherSkeleton{ //impl per ereditarietà

    //public class DispatcherImpl implements IDispatcher //impl per delega

    private int command = -1;

    /*
     * Elementi che identificano la coda circolare
     */
    private int data[]; //gli elementi sono memorizzati in un array gestito in maniera circolare

    private int size;
    private int elem;
    private int tail;
    private int head;

    /*
     * oggetti per la sincronizzazione tramite variabili condition
     */
    private Lock lock;
    private Condition spa_disp;
    private Condition mes_disp;
    
    /*
     * Nel costruttore inizializziamo la coda circolare
     * e le variabili per la sincronizzazione
     */
    public DispatcherImpl(){

        size=5;
        elem=0;
        data = new int[size];
        tail=head=0;

        lock = new ReentrantLock();
        spa_disp = lock.newCondition();
        mes_disp = lock.newCondition();

    }
    
    //metodi di utilità
    private boolean full(){
        if(elem==size){
            return true;
        }
        return false;
    }

    private boolean empty(){
        if(elem == 0){
            return true;
        }
        return false;
    }

    private int getSize(){
        return elem;
    }

    @Override
    public void sendCmd(int command) {
        
        lock.lock();
        try{
            //quando il vettore è pieno non posso aggiungere, mi fermo
            while(this.full()){
                System.out.println("Non posso creare, coda piena!");
                spa_disp.await();
            }

            data[tail%size]=command;
            System.out.println("[Send] Ho aggiunto: "+command+" alla posizione "+ tail%size);

            elem=elem+1;
            tail=tail+1;

            mes_disp.signal(); //notifico

        } catch (InterruptedException e) {
            e.printStackTrace();

        }finally{
            lock.unlock();
        }

    }

    @Override
    public int getCmd() {
        
        lock.lock();
        try{
            //quando la coda è vuota non posso prelevare -> wait
            while(this.empty()){
                System.out.println("Non posso prelevare, coda vuota!");
                mes_disp.await();
            }

            this.command = data[head%size];
            System.out.println("[Recv] getCmd: "+this.command+" alla posizione "+head%size);

            elem=elem-1;
            head=head+1;

            spa_disp.signal(); //notifico


        } catch (InterruptedException e) {
            e.printStackTrace();

        }finally{
            lock.unlock();
        }

        return this.command;
    }
    
}
