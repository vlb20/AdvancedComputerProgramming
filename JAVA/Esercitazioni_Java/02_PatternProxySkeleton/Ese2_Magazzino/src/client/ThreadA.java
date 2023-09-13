package client;

import java.util.Random;

import servizi.IMagazzino;

public class ThreadA extends Thread{
    
    public ThreadA(){
        super();
    }

    public void run(){
        
        IMagazzino magazzino = new ProxyClient();

        for(int i=0; i<3; i++){

            Random rand = new Random();
            
            int t = rand.nextInt(2)+2;

            try {
                Thread.sleep(t*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int val = rand.nextInt(100);

            if(val%2==0){

                magazzino.deposita("laptop", val);
                System.out.println("[ThreadA] Deposito laptop con id: "+val);

            }else{

                magazzino.deposita("smartphone", val);
                System.out.println("[ThreadA] Deposito smarthphone con id: "+val);

            }

        }

    }

}
