package coda;

public abstract class CodaWrapper implements Coda {
    
    protected Coda coda;

    public CodaWrapper(Coda c){
        coda=c;
    }

    /*
     * Implementiamo i metodi empty-full-getSize
     *
     * Non implementiamo invece Inserisci e Prelvea, al fine
     * di forzarne l'implementazione da parte dei concrete decorator
     * (per es. CodaWrapperSynchr) -> a tal fine, CodaWrapper è una classe astratta
     */

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
