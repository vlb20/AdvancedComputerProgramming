package coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaWrapperLock extends CodaWrapper{

    private Lock lock;
    private Condition empty;
    private Condition full;

    public CodaWrapperLock(Coda c) {
        super(c);

        lock = new ReentrantLock();

        empty = lock.newCondition();
        full = lock.newCondition();
    }

    @Override
    public void inserisci(int i) {

        lock.lock();

        try{

            while(coda.full()){
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            coda.inserisci(i);
            full.signal();

        }finally{
            lock.unlock();
        }

    }

    @Override
    public int preleva() {

        int x=0;

        lock.lock();

        try{

            while(coda.empty()){
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            x = coda.preleva();
            empty.signal();

        }finally{
            lock.unlock();
        }

        return x;

    }
    
}
