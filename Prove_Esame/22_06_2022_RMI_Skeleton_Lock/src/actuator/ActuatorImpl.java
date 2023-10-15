package actuator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import service.IController;

public class ActuatorImpl extends ActuatorSkeleton{

    private Lock lock;
    private String filename;

    public ActuatorImpl(int port, String filename) {
        super(port);
        this.filename=filename;
        lock=new ReentrantLock();

        try {

            Registry registry = LocateRegistry.getRegistry();
            IController controller = (IController) registry.lookup("myController");
            controller.addActuator(port);

        } catch (RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean execute(String attributi) {

        if(!lock.tryLock()){
            //se il lock non è libero
            return false;
        }else{
            StringTokenizer tokenizer = new StringTokenizer(attributi, " ");
            String type = tokenizer.nextToken();
            int data = Integer.parseInt(tokenizer.nextToken());
            System.out.println("[ActuatorImpl] Ricevuto tipo di richiesta: "+type+" con dato: "+data);
            FileWriter fw;
            try {
                
                fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                
                pw.write("Type: "+type+" - Data: "+data+"\n");
                pw.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Random randint = new Random();
            try {
                Thread.sleep((randint.nextInt(5)+1)*1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.unlock();
            return true;
        }

        

    }
    
}
