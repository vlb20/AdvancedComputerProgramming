package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import printer.PrinterSkeleton;
import services.IDispatcher;
import services.IPrinter;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    private Vector<IPrinter> printers;

    protected DispatcherImpl() throws RemoteException {
        super();
        printers = new Vector<IPrinter>();
    }

    @Override
    public boolean printRequest(String docName) throws RemoteException {
        
        boolean result = false;
        int size = printers.size();

        for(int i=0; i<size; i++){
            if(printers.get(i).print(docName)==true){
                result = true;
            }
        }

        return result;

    }

    @Override
    public void addPrinter(PrinterSkeleton printer) throws RemoteException {
        printers.add(printer);
        System.out.println("[Dispatcher] Aggiunta Printer!");
    }
    
}
