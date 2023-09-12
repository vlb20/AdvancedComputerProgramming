package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import service.ICounter;

public abstract class CounterSkel implements ICounter{
    
    public void runSkeleton(){

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            
            serverSocket = new ServerSocket(2500); //socket server
            System.out.println("Server in ascolto sulla porta 2500");

            while(true){

                socket = serverSocket.accept();//in attesa di richiesta di connessione da parte del client
                CounterWorker st = new CounterWorker(socket, this);
                st.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
    