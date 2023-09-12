package server;

import service.ICounter;

public class CounterImpl implements ICounter{

    private int count;

    public CounterImpl(){
        count = 0;
    }

    @Override
    public synchronized void inc() {
        count++;
    }

    @Override
    public synchronized void sum(int value) {
        count += value;
    }

    @Override
    public int get() {
        return count;
    }

    @Override
    public synchronized void square() {
        count = count*count;
    }
    
}
