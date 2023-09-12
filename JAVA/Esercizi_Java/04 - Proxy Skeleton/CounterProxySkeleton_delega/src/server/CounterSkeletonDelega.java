package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import service.ICounter;

/* NOTA: Lo Skeleton implementato per delega non è più una classe astratta che implementa solo il metodo runSkeleton
 * ma anche tutti i metodi invocabili sull'oggetto remoto. L'implementazione dei metodi remoti è sempre fatta in CounterImpl 
 */
public class CounterSkeletonDelega implements ICounter{

    private ICounter counter;

    public CounterSkeletonDelega(CounterImpl count){
        counter=count;
    }

    public void runSkeleton(){

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            
            serverSocket = new ServerSocket(2500); //socket server
            System.out.println("Server in ascolto sulla porta 2500");

            while(true){

                socket = serverSocket.accept();
                CounterWorker st = new CounterWorker(socket,this);
                st.start();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void inc() {
        counter.inc();
    }

    @Override
    public void sum(int value) {
        counter.sum(value);
    }

    @Override
    public int get() {
        return counter.get();
    }

    @Override
    public void square() {
        counter.square();
    }
    
}
