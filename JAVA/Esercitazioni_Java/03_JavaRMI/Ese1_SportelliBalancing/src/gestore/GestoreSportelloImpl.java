package gestore;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import servizi.IGestoreSportello;
import servizi.ISportello;

public class GestoreSportelloImpl extends UnicastRemoteObject implements IGestoreSportello{

    private static final long serialVersionUID = 20L;

    private Vector<ISportello> sportelli; //lista di sportelli (observers) registrati

    protected GestoreSportelloImpl() throws RemoteException {
        
        sportelli = new Vector<ISportello>();

    }

    @Override
    public boolean sottoponiRichiesta(int idCliente) throws RemoteException {

        boolean result = false;
        int i=0;

        while((!result)&&(i<sportelli.size())){
            result = sportelli.get(i).serviRichiesta(idCliente);
            i++;
        }

        System.out.println("[Gestore] Richiesta da "+idCliente+" terminata con esito "+result);

        return result;
    }

    @Override
    public void sottoscrivi(ISportello sportello) throws RemoteException {

        sportelli.add(sportello);
        System.out.println("[Gestore] Nuovo sportello sottoscritto!");

    }
    
}
