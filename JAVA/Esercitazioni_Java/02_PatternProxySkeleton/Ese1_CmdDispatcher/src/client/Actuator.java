package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import service.IDispatcher;

public class Actuator {
    
    public static void main(String[] args) {
        
        IDispatcher dispatcher = new ProxyClient(args[0], Integer.valueOf(args[1]));
        int command = 0;

        try {
            
            FileOutputStream fileOut = new FileOutputStream("./cmdlog.txt");
            PrintStream outStream = new PrintStream(fileOut);

            while(true){

                command = dispatcher.getCmd();
                System.out.println("[ACT] ricevuto comando #"+command);
                outStream.println("comando = "+command);

                Thread.sleep(1000);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    

}
