package Coda;

public class CodaImpl implements ICoda{

    private String[] data;
    private int elem;
    private int head;
    private int tail;
    private int size;

    public CodaImpl(int dim){
        size=dim;
        data = new String[size];
        elem=head=tail=0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return elem==size;
    }

    @Override
    public boolean full() {
        return elem==0;
    }

    @Override
    public void inserisci(String command) {
        data[tail%size]=command;
        tail=tail+1;
        elem=elem+1;
    }

    @Override
    public String preleva() {
        String x=data[head%size];
        head=head+1;
        elem=elem-1;
        return x;
    }
    


}
