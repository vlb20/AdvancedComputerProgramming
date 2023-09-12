package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    
    public static void main(String[] args) {
        
        try {
            
            Socket s = new Socket("127.0.0.1", 8050);
            System.out.println("[Client]: socket creata.");

            /*
             * creazione degli stream per la comunicazione
             * con la socket lato server
             */

            DataOutputStream toServer = new DataOutputStream(s.getOutputStream());
            DataInputStream fromServer = new DataInputStream(s.getInputStream());

            //invio di una stringa
            toServer.writeUTF("Ciao server...Forza Napoli!");

            //attesa e stampa della stringa ricevuta dal server
            System.out.println("[Client]: attesa risposta...");
            String resp = fromServer.readUTF();
            System.out.println("[Client]: risposta server: "+resp);

            //chiusura stream e socket
            toServer.close();
            fromServer.close();
            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
