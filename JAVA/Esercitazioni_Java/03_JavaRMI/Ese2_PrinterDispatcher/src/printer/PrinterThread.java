package printer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import services.IPrinter;

public class PrinterThread extends Thread{

    private Socket s;
    private IPrinter p;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public PrinterThread(Socket s, IPrinter p){
        this.s = s;
        this.p = p;

        try {

            dataIn = new DataInputStream(s.getInputStream());
            dataOut = new DataOutputStream(s.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){

        try {
            
            String val = dataIn.readUTF();
            System.out.println("[PrinterThread] Ho letto il valore!");

            Boolean ok = p.print(val);

            System.out.println("[PrinterThread] Print effettuata, invio valore...");

            dataOut.writeBoolean(ok);

            dataOut.flush();
            dataOut.close();
            dataIn.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
