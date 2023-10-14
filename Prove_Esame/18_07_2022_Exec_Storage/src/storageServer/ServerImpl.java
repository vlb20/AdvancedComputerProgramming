package storageServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import service.IStorage;

public class ServerImpl extends UnicastRemoteObject implements IStorage{

    private Lock lock;

    public ServerImpl() throws RemoteException {
        super();

        lock = new ReentrantLock();
    }

    @Override
    public void store(String reqType, int result) throws RemoteException {

        lock.lock();

        try{

            System.out.println("[StorageServer] Ho ricevuto la richiesta: "+reqType+" con risultato: "+result);
            
            try {
            
                FileWriter fw = new FileWriter("result.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.write(reqType+": "+result+"\n");
                pw.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        }finally{
            lock.unlock();
        }

    }
    
}
