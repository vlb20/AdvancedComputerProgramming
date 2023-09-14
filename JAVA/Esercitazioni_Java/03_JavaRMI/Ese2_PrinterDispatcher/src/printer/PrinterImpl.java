package printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

import services.IPrinter;

public class PrinterImpl implements IPrinter{

    private Semaphore mutex;

    public PrinterImpl(){
        mutex = new Semaphore(1); //gestisce una sola richiesta in concorrenza
    }

    @Override
    public boolean print(String docName) {
        
        if(!(mutex.tryAcquire())){
            System.out.println("[Printer] Printer occupata!");
            return false;
        }else{
            System.out.println("[Printer] Printer libera: stampo "+docName);

            try {

                FileWriter fw = new FileWriter("docs.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                pw.append(docName + "\n");

                pw.flush();
                pw.close();
                bw.close();
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                mutex.release();
            }

            return true;
        }

    }

}
