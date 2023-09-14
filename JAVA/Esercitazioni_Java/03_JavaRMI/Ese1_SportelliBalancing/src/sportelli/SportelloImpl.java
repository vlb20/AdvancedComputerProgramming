package sportelli;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.Semaphore;

import servizi.ISportello;

public class SportelloImpl extends UnicastRemoteObject implements ISportello{

    private static final long serialVersionUID = 2L;

    private Semaphore maxReq;
    private Semaphore maxCon;

    public SportelloImpl() throws RemoteException {
        
        maxReq = new Semaphore(5);//Richieste in concorrenza 3 + 2 in attesa
        maxCon = new Semaphore(3);

    }

    @Override
    public boolean serviRichiesta(int idCliente) throws RemoteException {

        boolean result = false;

        if(!maxReq.tryAcquire()){//Provo ad acquisire un permesso -> se esce false ritorno false

            System.out.println("[Sportello] Numero di richieste massime raggiunte!");
            System.out.println("[Sportello] Richiesta dal Client con id: "+idCliente+" non servita!...Forza Napoli");
            return result;

        }

        try {

            maxCon.acquire();
            
            Random rand = new Random();
            Thread.sleep((rand.nextInt(5)+1)*1000); //attendo tra 1 e 5 secondi

            FileWriter fw = new FileWriter("request.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(idCliente);
            pw.flush();

            pw.close();
            bw.close();
            fw.close();

            result = true;

            System.out.println("[Sportello] Richiesta dal Client con id: "+idCliente+ "servita! FORZA NAPOLI!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        
        }finally{
            maxCon.release();
            maxReq.release();
        }

        return result;

    }


}
