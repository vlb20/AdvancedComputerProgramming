package client;

import java.rmi.RemoteException;
import java.util.Random;

import servizi.IGestoreSportello;

public class ClientThread extends Thread{

    private int numRequest;
    private IGestoreSportello gestore;

    public ClientThread(int numRequest, IGestoreSportello gestore){

        this.numRequest = numRequest;
        this.gestore = gestore;

    }

    @Override
    public void run(){

        Random rand = new Random();

        for(int i=0; i<numRequest; i++){

            try {

                Thread.sleep((rand.nextInt(3)+1)*1000);
                
                int idCliente = rand.nextInt(100)+1;

                boolean result = gestore.sottoponiRichiesta(idCliente);
                System.out.println("[ClientThread] Richiesta servita con esito " + result);

            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }

        }

    }

}
