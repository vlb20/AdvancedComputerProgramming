package prodcons;

public class Buffer {
    
    private long content;
    private boolean full;

    public Buffer(){

        content = 0;
        /*
         * Condizione iniziale: FALSE
         * FALSE = contenuto buffer non prodotto
         * TRUE = contenuto buffer prodotto
         */
        full = false;
    }

    public synchronized void produci(){

        System.out.println( Thread.currentThread().getName() + ": invocazione produci");

        while(full){//finchè è pieno - il produttore si mette in attesa
            System.out.println(Thread.currentThread().getName()+ ": in attesa (buffer pieno)");

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //una volta che si è liberato il buffer -> PRODUZIONE
        content = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + ": prodotto = "+content);

        //aggiorno la variabile che indica che il buffer è pieno
        full = true;

        notifyAll();

    }

    public synchronized void consuma(){

        System.out.println(Thread.currentThread().getName()+": invocazione consuma");

        while(!full){//finchè NON è pieno -> il consumatore attende
            System.out.println(Thread.currentThread().getName()+": in attesa (buffer vuoto)");

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //una volta che il buffer è pieno -> CONSUMAZIONE
        System.out.println(Thread.currentThread().getName()+": consumato = "+content);

        //aggiorno la variabile che indica che il buffer è vuoto
        full=false;

        notifyAll();

    }

}
