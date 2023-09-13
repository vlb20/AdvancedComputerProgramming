package codaimpl;

import coda.Coda;
import coda.CodaWrapper;

public class CodaWrapperSynchr extends CodaWrapper{

    public CodaWrapperSynchr(Coda c) {
        super(c);
    }

    /*
	 * implementazione delle procedure di accesso alla
	 * coda con blocchi sincronizzati
	 */

    @Override
    public void inserisci(int i) {

        synchronized(coda){

            while(coda.full()){//finchè la coda è piena->attendo
                
                try {
                    coda.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //Una volta che la coda non è più piena produco
            coda.inserisci(i);

            //Notifico tutti quelli che stanno nel wait set
            coda.notifyAll();
            
        }

    }

    @Override
    public int preleva() {
        
        int x=0;

        synchronized(coda){

            while(coda.empty()){//finchè la coda è vuota -> attendo

                try {
                    coda.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //Una volta che la coda non è più vuota -> prelevo
            x = coda.preleva();

            coda.notifyAll();
        }

        return x;
    }


    
}
