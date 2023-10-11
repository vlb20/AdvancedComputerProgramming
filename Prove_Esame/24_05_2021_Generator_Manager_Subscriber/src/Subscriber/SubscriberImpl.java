package Subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Service.ISubscriber;

public class SubscriberImpl implements ISubscriber{

    private String filename;

    public SubscriberImpl(String filename){
        this.filename = filename;
    }

    @Override
    public void notifyAlert(int criticality) {

        System.out.println("[SUBSCRIBER] Ho ricevuto un alert con criticità: "+criticality);
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(String.valueOf(criticality)+"\n");
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
