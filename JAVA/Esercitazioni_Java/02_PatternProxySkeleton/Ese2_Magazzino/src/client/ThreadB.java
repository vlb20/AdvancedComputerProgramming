package client;

import java.util.Random;

import servizi.IMagazzino;

public class ThreadB extends Thread{
    
    public ThreadB(){
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

                int id = magazzino.preleva("laptop");
                System.out.println("[ThreadA] Prelevo laptop con id: "+id);

            }else{

                int id = magazzino.preleva("smartphone");
                System.out.println("[ThreadA] Prelevo smarthphone con id: "+id);

            }

        }

    }

}
