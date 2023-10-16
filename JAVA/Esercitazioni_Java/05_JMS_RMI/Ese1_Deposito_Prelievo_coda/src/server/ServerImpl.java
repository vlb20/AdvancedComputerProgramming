package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import coda.ICoda;

public class ServerImpl extends UnicastRemoteObject implements IServer{

    private ICoda coda;

    protected ServerImpl(ICoda coda) throws RemoteException {
        super();
        this.coda = coda;
    }


    @Override
    public void deposita(int id_articolo) throws RemoteException {
        System.out.println("Deposito id_articolo: "+id_articolo);
        coda.inserisci(id_articolo);
    }

    @Override
    public int preleva() throws RemoteException {
        int id_articolo = coda.preleva();
        System.out.println("Prelievo id_articolo: "+id_articolo);
        return id_articolo;
    }
    
}
