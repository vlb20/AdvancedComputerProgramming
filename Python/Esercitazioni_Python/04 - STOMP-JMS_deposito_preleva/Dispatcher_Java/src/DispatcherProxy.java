import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

public class DispatcherProxy implements IDispatcher{

    private String addr;
    private int port;
    private QueueSession qs;
    private Queue qresponse;

    public DispatcherProxy(String addr, int port, QueueSession qs, Queue qresponse) {
        
        this.addr = new String(addr);
        this.port = port;
        this.qs = qs;
        this.qresponse = qresponse;

    }

    @Override
    public void deposita(int valore) {
        
        try {

            Socket s = new Socket(addr, port);
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());

            // NOTE: A BufferedReader is used to receive data from a Python application, since it allows using the readLine method
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

            dataOut.writeUTF("deposita-"+valore);

            String result = dataIn.readLine(); // forza il proxy ad attendere una risposta dal server
            // nel caso di metodo che restituisce void

            s.close();

            //rispondo tramite JMS al client Python
            TextMessage message = qs.createTextMessage(result+"-"+valore);
            QueueSender sender = qs.createSender(qresponse);
            sender.send(message);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public int preleva() {
        
        String x = null;

        try {
            
            Socket s = new Socket(addr, port);

            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());

            BufferedReader dataIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

            dataOut.writeUTF("preleva");
            x = new String(dataIn.readLine());

            s.close();

            //rispondo tramite JMS al client pyhon
            TextMessage message = qs.createTextMessage("Prelevato-"+x);
            QueueSender sender = qs.createSender(qresponse);
            sender.send(message);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Integer.valueOf(x);

    }
    
}
