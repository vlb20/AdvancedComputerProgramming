package printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;
import services.IPrinter;

//Per delega
public class PrinterSkeleton implements IPrinter{

    private PrinterImpl printer;
    private int port;

    public PrinterSkeleton(int port){
        printer = new PrinterImpl();
        this.port=port;
    }

    public void runSkeleton(){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher stub = (IDispatcher)rmiRegistry.lookup("dispatcher");

            stub.addPrinter(this); //aggiungo il printer e vado in while true per la connessione

            ServerSocket serversocket = new ServerSocket(port);
            Socket socket = null;
            System.out.println("Inizializzo server...");
            while(true){
                socket = serversocket.accept();
                PrinterThread t = new PrinterThread(socket,printer);
                t.start();
            }
        }catch (RemoteException e){
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean print(String docName) {
        return printer.print(docName);
    }

}
