package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Client {
    
    public static void main(String[] args) {

        if(args.length != 1){
            System.out.println("Inserisci il parametro [nomePrinter]");
            return;
        }
        
        Hashtable <String,String> p = new Hashtable<String,String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.printRequest", "printRequest");

        try {

            Context jndiContext = new InitialContext(p);
            
            QueueConnectionFactory qcf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue queue =(Queue) jndiContext.lookup("printRequest");
            
            QueueConnection qc = qcf.createQueueConnection();
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueSender sender = qs.createSender(queue);

            for(int i=0; i<5; i++){
                MapMessage mm = qs.createMapMessage();
                Random rand = new Random();
                int n = rand.nextInt(41);
                String docName = "doc"+n;
                mm.setString("docName", docName);
                mm.setString("printerName", args[0]);
                System.out.println("Invio il messaggio con: "+docName);
                sender.send(mm);
            }

            sender.close();
            qs.close();
            qc.close();
            

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
