package Coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaWrapperLock extends CodeWrapper{

    private Lock lock;
    private Condition spazioDisp;
    private Condition comandiDisp;

    public CodaWrapperLock(ICoda c) {
        super(c);
        lock = new ReentrantLock();
        spazioDisp = lock.newCondition();
        comandiDisp = lock.newCondition();
    }

    @Override
    public void inserisci(String command) {
        
        try{
            lock.lock();
            while(c.full()){
                try {
                    spazioDisp.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            c.inserisci(command);
            comandiDisp.signal();
        }finally{
            lock.unlock();
        }

    }

    @Override
    public String preleva() {

        String x = "";
        try{
            lock.lock();
            while(c.empty()){
                try {
                    comandiDisp.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            x = c.preleva();
            spazioDisp.signal();
        }finally{
            lock.unlock();
        }

        return x;

    }
    
}
