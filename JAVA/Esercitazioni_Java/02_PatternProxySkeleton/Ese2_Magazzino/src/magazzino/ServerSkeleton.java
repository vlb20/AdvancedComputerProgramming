package magazzino;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import servizi.IMagazzino;

public abstract class ServerSkeleton implements IMagazzino{

    public void runSkeleton(){

        ServerSocket serversocket = null;
        Socket client = null;

        try {
            
            serversocket = new ServerSocket(8000);
            System.out.println("Server attivo; attendo richieste...");

            while(true){

                client = serversocket.accept();

                ServerThread t = new ServerThread(client, this);
                t.start();

            }

        } catch (IOException e) {
            System.err.println("Problemi nello skeleton");
        }

    }
}
