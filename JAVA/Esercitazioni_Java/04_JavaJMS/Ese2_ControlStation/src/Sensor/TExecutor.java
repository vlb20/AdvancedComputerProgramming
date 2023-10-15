package Sensor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Coda.ICoda;

public class TExecutor extends Thread{

    private ICoda coda;
    
    public TExecutor(ICoda coda) {
        this.coda = coda;
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(10000);
                while(!coda.empty()){
                    String x = coda.preleva();
                    System.out.println("[TExecutor] Prelevo il comando: "+x);
                    FileWriter fw = new FileWriter("CmdLog.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);
                    pw.write(x+"\n");
                    pw.close();
                }
            } catch (InterruptedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
