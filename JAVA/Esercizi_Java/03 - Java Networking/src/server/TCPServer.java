package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    
    public static void main(String[] args) {
        
        try {
            ServerSocket server = new ServerSocket(8050);
            System.out.println("[Server]: in attesa su porta 8050.");

            while(true){
                Socket s = server.accept();//mi pongo in attesa di richieste di connessione da parte del client
                //una volta ricevuta la richiesta, l'oggetto ServerSocket crea una socket che rappresenta la connessione TCP con il client
                System.out.println("[Server]: nuovo client, avvio del thread Worker.");

                /*
                 * creazione ed avvio del thread worker responsabile della
                 * gestione della connessione con il client
                 */

                WorkerServer w = new WorkerServer(s); //gli passo la socket creata
                w.start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
