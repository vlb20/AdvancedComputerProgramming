package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import service.IPrinter;

public class TDispatcher extends Thread{

    private MapMessage mm;

    public TDispatcher(MapMessage mm) {
        this.mm = mm;
    }

    public void run(){

        try {

            String docName = mm.getString("docName");
            String printerName = mm.getString("printerName");
            
            System.out.println("Ricevuto nomeDocumento: "+docName+" e nomePrinter: "+printerName);
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = (IPrinter) rmiRegistry.lookup(printerName);
            printer.printDoc(docName);

        } catch (JMSException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    


}
