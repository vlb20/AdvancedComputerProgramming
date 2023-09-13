package client;

import service.IDispatcher;

public class Client {
    
    public static void main(String[] args) {
        
        IDispatcher dispatcher = new ProxyClient(args[0], Integer.valueOf(args[1]));

        for(int i=0; i<5; i++){

            WorkerC t = new WorkerC(dispatcher);
            t.start();
        }
    }
}
