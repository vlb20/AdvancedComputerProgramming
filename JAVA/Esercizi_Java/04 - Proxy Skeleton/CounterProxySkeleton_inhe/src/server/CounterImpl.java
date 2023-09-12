package server;

//CASO 2: implementazione per delega
//import service.*;
//
//public class CounterImpl implements ICounter {

//CASO 1: implementazione per ereditarieta'
public class CounterImpl extends CounterSkel{

    private int count;

    public CounterImpl(){
        count=0;
    }

    @Override
    public void inc() {
        count++;
    }

    @Override
    public void sum(int value) {
        count += value;
    }

    @Override
    public int get() {
        return count;
    }

    @Override
    public void square() {
        count=count*count;
    }

}
