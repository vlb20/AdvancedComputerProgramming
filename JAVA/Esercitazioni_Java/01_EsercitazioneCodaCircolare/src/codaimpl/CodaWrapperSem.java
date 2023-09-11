package codaimpl;

import java.util.concurrent.Semaphore;

import coda.Coda;
import coda.CodaWrapper;

public class CodaWrapperSem extends CodaWrapper{

    private Semaphore postiDisp;
    private Semaphore elemDisp;

    public CodaWrapperSem(Coda c) {
        super(c);
        
        postiDisp = new Semaphore( coda.getSize()); //tanti permessi quanto la dimensione della coda
        elemDisp = new Semaphore(0); //all'inizio non è stato prodotto nullo -> quindi nessun elemento è disponibile
    }

    @Override
    public void inserisci(int i) {
        
        try {
            postiDisp.acquire(); //acquisisco un permesso per produrre

            try{

                synchronized(coda){
                    coda.inserisci(i);
                }

            }finally{
                elemDisp.release(); //una volta prodotto -> rilascio un permesso per chi deve consumare
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int preleva() {
        
        int x=0;

        try {
            elemDisp.acquire(); //acquisisco un permesso per consumare un elemento

            try{
                
                synchronized(coda){
                    x = coda.preleva();
                }

            }finally{
                postiDisp.release(); //una volta consumato -> rilascio un permesso per produrre
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return x;
    }
    
}
