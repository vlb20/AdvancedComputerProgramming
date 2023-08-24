package Client;

import java.util.Random;

public class WorkerC extends Thread{
    
    private Proxy proxy;

    public WorkerC(){//chiamo il super
        super();
    }

    @Override
    public void run(){
        //nella funzione di run, chiamo il metodo di scrittura 3 volte
        //CHIAMANDO OGNI VOLTA UN PROXY

        for(int i=0; i<3; i++){
            proxy = new Proxy(); //ogni volta nuovo!

            try{
                sleep(1000);

                Random rand = new Random();

                int x = rand.nextInt(4); //valore randomico tra 0 e 3

                System.out.println("[Ho inviato]: "+x);

                proxy.sendCmd(x); //avvio la send!

                System.out.println("[Client] Valore inviato!");

            }catch (InterruptedException e){
                System.err.println("Interrotto per eccezione generica!");
            }
        }
    }
}
