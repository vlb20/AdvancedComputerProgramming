package printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.IPrinter;

public class PrinterImpl extends UnicastRemoteObject implements IPrinter{

    public PrinterImpl() throws RemoteException{
        super();
    }

    @Override
    public synchronized void printDoc(String nomeDoc) throws RemoteException {
        
        System.out.println("Nome del documento ricevuto: "+nomeDoc);
        try {
            FileWriter fw = new FileWriter("docs.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(nomeDoc+"\n");
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
