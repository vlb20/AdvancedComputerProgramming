package coda;

public class CodaImpl implements ICoda{

    private int data[];

    private int size;
    private int elem;

    private int tail;
    private int head;

    public CodaImpl(int dim){
        size=dim;
        elem=0;
        data = new int[size];
        tail=head=0;
    }

    @Override
    public void inserisci(int i) {
        data[tail%size]=i;
        tail=tail+1;
        elem=elem+1;
    }

    @Override
    public int preleva() {
        int x = data[head%size];
        head=head+1;
        elem=elem-1;
        return x;
    }

    @Override
    public boolean empty() {
        return elem==0;
    }

    @Override
    public boolean full() {
        return elem==size;
    }

    @Override
    public int getSize() {
        return size;
    }
    
}
