package rider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import service.IRider;

public class RiderImpl implements IRider{

    private String filename;

    public RiderImpl(String filename) {

        this.filename = filename;

    }

    @Override
    public void notifyOrder(int id, String address) {
        
        System.out.println("Order - ID: "+id+" - Address: "+address);
        FileWriter fw;
        try {

            fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String ordine = id+" "+address;
            pw.write(ordine+"\n");
            pw.close();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
    
}
