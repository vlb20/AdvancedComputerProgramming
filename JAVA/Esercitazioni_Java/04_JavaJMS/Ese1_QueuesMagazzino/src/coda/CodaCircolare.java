package coda;

public class CodaCircolare implements Coda{

    private int data[]; //gli elementi sono memorizzati in un array gestito in maniera circolare

    private int size;
    private int elem;

    private int tail;
    private int head;

    public CodaCircolare(int s){
        size=s;
        elem=0;
        data = new int[size];
        tail=head=0;
    }

    @Override
    public void inserisci(int i) {

        data[tail%size] = i;

        elem=elem+1;
        tail=tail+1;

    }

    @Override
    public int preleva() {

        int x = data[head%size];

        elem=elem-1;
        head=head+1;

        return x;

    }

    @Override
    public boolean empty() {
        if(elem==0){
            return true;
        }
        return false;
    }

    @Override
    public boolean full() {
        if(elem==size){
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }
    
}
