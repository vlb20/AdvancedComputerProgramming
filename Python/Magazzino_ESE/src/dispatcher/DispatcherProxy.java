package dispatcher;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

public class DispatcherProxy {
    private QueueSession qsession;
    private Queue qresponse;

    public DispatcherProxy(QueueSession qs, Queue qr){
        qsession=qs;
        qresponse=qr;
    }

    public void deposita(int val){
        try {
            Socket s = new Socket("127.0.0.1",2500);
            DataOutputStream ostream = new DataOutputStream(s.getOutputStream());
            BufferedReader istream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String concat = "deposita-"+val;
            ostream.writeUTF(concat);
            String resp = istream.readLine();
            System.out.println("[DISPATCHER] Ricevo: "+resp);
            TextMessage m = qsession.createTextMessage(resp);
            QueueSender sender = qsession.createSender(qresponse);
            sender.send(m);
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int preleva(){
        int value = 0;
        try {
            Socket s = new Socket("127.0.0.1",2500);
            DataOutputStream ostream = new DataOutputStream(s.getOutputStream());
            BufferedReader istream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ostream.writeUTF("preleva");
            String resp = istream.readLine();
            System.out.println("[DISPATCHER] Ricevo: "+resp);
            TextMessage m = qsession.createTextMessage(resp);
            QueueSender sender = qsession.createSender(qresponse);
            sender.send(m);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
