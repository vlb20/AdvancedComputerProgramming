package Client;

public class Client {
    public static void main(String[] args){

        for(int i=0; i<5; i++){//creo, nel client 5 thread worker C!
            System.out.println("Avvio nuovo Thread...");

            WorkerC worker = new WorkerC();

            worker.start();

        }
    }
}
