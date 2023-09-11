public class WorkerThread implements Runnable{

    private String message;

    public WorkerThread(String s){
        this.message=s;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" (Start) message = "+message);
        processmessage(); //Chiama la funzione che fa addormentare il thread per 2 secondi
        System.out.println(Thread.currentThread().getName()+" (End)");//stampa il nome del thread
    }

    public void processmessage(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
