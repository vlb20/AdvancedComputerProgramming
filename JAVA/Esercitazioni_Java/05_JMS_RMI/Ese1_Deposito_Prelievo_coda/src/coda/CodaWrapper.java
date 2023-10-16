package coda;

public abstract class CodaWrapper implements ICoda{
    
    protected ICoda coda;

    public CodaWrapper (ICoda c){
        coda=c;
    }

    public boolean empty(){
        return coda.empty();
    }

    public boolean full(){
        return coda.full();
    }

    public int getSize(){
        return coda.getSize();
    }

}
