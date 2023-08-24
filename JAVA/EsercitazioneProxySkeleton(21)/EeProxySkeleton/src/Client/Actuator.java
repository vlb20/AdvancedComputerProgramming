package Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Actuator {
    public static void main(String[] args){

        while(true){ //dobbiamo invocare getCmd ogni secondo
            Proxy proxy = new Proxy(); //qui vado di while true e prendo ogni volta un proxy!

            //per scrivere su file

            Integer x = proxy.getCmd();
            //leggo e mostro il risultato!
            System.out.println("valore comando letto...");

            if(x==0){
                System.out.println("Comando letto "+x+": LEGGI");
            }else if(x==1){
                System.out.println("Comando letto "+x+": SCRIVI");
            }else if(x==2){
                System.out.println("Comando letto "+x+": CONFIGURA");
            }else{
                System.out.println("Comando letto "+x+": RESET");
            }

            try{
                Thread.sleep(1000); //ogni secondo
                BufferedWriter wr = new BufferedWriter(new FileWriter("cmdlog.txt")); //scrivo su file

                wr.append(x.toString());
                wr.close();
            }catch (InterruptedException | IOException e){
                System.err.println("Errore interruzione!");
            }

        }
    }
}