package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WorkerServer extends Thread{

    private Socket s;

    public WorkerServer(Socket skt){
        s=skt;
    }

    public void run(){

        /*
         * Creazione degli stream per la comunicazione
         * con la socket lato client
         */

        try {
            
            DataInputStream fromClient = new DataInputStream(s.getInputStream());
            DataOutputStream toClient = new DataOutputStream(s.getOutputStream());

            //attesa e stampa della string inviata dal client
            System.out.println("[Server-Worker]: attesa stringa dal client...");
            String st = fromClient.readUTF();
            System.out.println("[Server-Worker]: stringa ricevuta < "+st+" >. Invio risposta...");

            //invio della stringa di risposta
            toClient.writeUTF("richiesta ricevuta...Forza Napoli Sempre");

            fromClient.close();
            toClient.close();
            s.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
