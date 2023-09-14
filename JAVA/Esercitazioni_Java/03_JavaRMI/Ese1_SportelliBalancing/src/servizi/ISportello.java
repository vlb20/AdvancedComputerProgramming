package servizi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISportello extends Remote{
    
    public boolean serviRichiesta (int idCliente) throws RemoteException;

}
