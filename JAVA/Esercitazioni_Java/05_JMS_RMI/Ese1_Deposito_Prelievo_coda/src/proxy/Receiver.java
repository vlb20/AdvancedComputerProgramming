package proxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.QueueSession;

import server.IServer;

public class Receiver extends Thread{

    private QueueSession qs;
    private MapMessage mm;

    public Receiver(QueueSession qs, MapMessage mm) {

        this.qs = qs;
        this.mm = mm;

    }

    public void run(){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IServer server = (IServer) rmiRegistry.lookup("myServer");

            String tipoRichiesta = mm.getString("tipoRichiesta");
            if(tipoRichiesta.equalsIgnoreCase("deposita")){

                int id_articolo = mm.getInt("id_articolo");
                System.out.println("[Receiver] Richiesta di deposito dell'articolo"+id_articolo);
                server.deposita(id_articolo);

            }else{
                int id_articolo = server.preleva();
                Sender sender = new Sender(id_articolo, qs, mm);
                sender.start();
            }
            
        } catch (RemoteException | NotBoundException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
