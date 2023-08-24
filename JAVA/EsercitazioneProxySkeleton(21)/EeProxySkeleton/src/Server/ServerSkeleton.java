package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Servizio.IDispatcher;

public abstract class ServerSkeleton implements IDispatcher{
    
    //PER EREDITARIETA'

    protected void runSkeleton(){//devo creare la socket e mettermi in attesa

        try {
            
            ServerSocket socket = new ServerSocket(3000);

            System.out.println ("Server attivo; avvio while loop... ");

            while(true){

                Socket client = socket.accept(); //aspetto connessione!

                //Avvio thread worker!
                WorkerS thread = new WorkerS(client, this);

                thread.start();//runnato!
            }

        } catch (IOException e) {
            System.err.println("Eccezione IO in runSkeleton!");
        }

    }
}
