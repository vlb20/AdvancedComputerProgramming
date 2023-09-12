package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import service.ICounter;

public class CounterProxy implements ICounter{

    private String host;
    private int port;

    //Costruttore -> richiede in ingresso l'indirizzo del server
    public CounterProxy(String host, int port){
        this.host=host;
        this.port=port;
    }

    /*
     * Metodi per l'invocazione remota dei servizi
     */
    
    @Override
    public void inc() {
        
        try {

            Socket soc = new Socket(host, port);
            DataOutputStream ostream = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));

            //marshalling
            ostream.writeUTF("inc");
            ostream.flush();

            soc.close();
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sum(int value) {
        
        try {

            Socket soc = new Socket(host, port);
            DataOutputStream ostream = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));

            //marshalling
            ostream.writeUTF("sum");
            ostream.writeInt(value);
            ostream.flush();

            soc.close();
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int get() {
        
        try {

            Socket soc = new Socket(host, port);
            DataOutputStream ostream = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
            DataInputStream istream = new DataInputStream(new BufferedInputStream(soc.getInputStream()));

            //marshalling
            ostream.writeUTF("get");
            ostream.flush();

            int x = istream.readInt();

            soc.close();

            return x;
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }

    @Override
    public void square() {
        
        try {

            Socket soc = new Socket(host, port);
            DataOutputStream ostream = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));

            //marshalling
            ostream.writeUTF("sqr");
            ostream.flush();

            soc.close();
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    

}
