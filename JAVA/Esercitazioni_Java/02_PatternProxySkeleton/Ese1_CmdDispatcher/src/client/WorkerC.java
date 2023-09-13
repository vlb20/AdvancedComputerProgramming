package client;

import service.IDispatcher;

public class WorkerC extends Thread{
    
    private IDispatcher dispatcher;

    public WorkerC(IDispatcher d){
        dispatcher = d;
    }

    @Override
    public void run(){

        for(int i=0; i<3; i++){

            int x = (int)(Math.random()*4);

            System.out.println("[Thread ID:"+this.getId()+"] "+ "[CLN] invio comando # "+x);

            dispatcher.sendCmd(x);
        }
    }
}
