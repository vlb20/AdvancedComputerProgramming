package printer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IPrinter;

public class Printer {
    
    public static void main(String[] args) {
        
        if(args.length!=1){
            System.out.println("Inserisci come paramtero il nome simbolico!");
            return;
        }

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = new PrinterImpl();
            rmiRegistry.rebind(args[0], printer);
            System.out.println("Printer pronto!");

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
