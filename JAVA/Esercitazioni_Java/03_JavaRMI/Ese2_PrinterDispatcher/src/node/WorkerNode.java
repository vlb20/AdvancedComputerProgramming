package node;

import java.rmi.RemoteException;
import java.util.Random;

import services.IDispatcher;

public class WorkerNode extends Thread{

    private IDispatcher dispatcher;

    public WorkerNode(IDispatcher dispatcher){
        super();
        this.dispatcher=dispatcher;
    }

    @Override
    public void run(){

        for(int i=0; i<3; i++){
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random rand = new Random();
            int n = rand.nextInt(50)+1;
            String docName = "doc"+n;
            
            try {

                dispatcher.printRequest(docName);
                System.out.println("Stampato "+docName);
            
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }


    }

}
