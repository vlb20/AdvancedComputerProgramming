package coda;

import java.util.concurrent.Semaphore;

import servizi.ICoda;

public class CodaWrapperSem extends CodaWrapper{

    private Semaphore spa_disp;
    private Semaphore mes_disp;

    public CodaWrapperSem(ICoda c) {
        super(c);
        spa_disp = new Semaphore(coda.size());
        mes_disp = new Semaphore(0);
    }

    @Override
    public void deposita(int id) {
        
        try {
            
            spa_disp.acquire();
            
            try{
                synchronized(coda){
                    coda.deposita(id);
                }
            }finally{
                mes_disp.release();
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int preleva() {
        
        int val =0;

        try {
            mes_disp.acquire();
            
            try{
                synchronized(coda){
                    val = coda.preleva();
                }
            }finally{
                spa_disp.release();
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return val;
    }
    
}
