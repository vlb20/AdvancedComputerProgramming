package codaimpl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import coda.Coda;
import coda.CodaWrapper;

public class CodaWrapperLock extends CodaWrapper{

    private Lock lock;
    private Condition empty;
    private Condition full;

    public CodaWrapperLock(Coda c) {
        super(c);
        
        lock = new ReentrantLock();

        //a differenza di synchronized, con i lock
        //è possibile creare N condizioni

        empty = lock.newCondition();
        full = lock.newCondition();
    }

    @Override
    public void inserisci(int i) {
        
        lock.lock();

        try{

            while(coda.full()){//finchè la coda è piena -> attendo sulla condizione vuoto
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //quando la coda non è più piena
            coda.inserisci(i);
            full.signal(); //segnalo i thread che aspettano sulla condizione di coda piena

        }finally{
            lock.unlock();
        }
    }

    @Override
    public int preleva() {
        
        int x = 0;

        lock.lock();

        try{

            while(coda.empty()){//finchè la coda è vuota -> attendo sulla condizione piena
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //quando la coda non è più vuota
            x = coda.preleva();
            empty.signal(); //segnalo i thread che aspettano di produrre

        }finally{
            lock.unlock();
        }

        return x;
    }
    
}
