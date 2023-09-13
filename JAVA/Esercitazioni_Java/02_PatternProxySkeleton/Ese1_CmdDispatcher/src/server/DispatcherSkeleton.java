package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import service.IDispatcher;

public abstract class DispatcherSkeleton implements IDispatcher{
    

    public void runSkeleton(){

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            
            serverSocket = new ServerSocket(8000);
            System.out.println("Server attivo; avvio while loop");

            while(true){

                socket = serverSocket.accept();

                WorkerServer t = new WorkerServer(socket, this);
                t.start();
                
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
