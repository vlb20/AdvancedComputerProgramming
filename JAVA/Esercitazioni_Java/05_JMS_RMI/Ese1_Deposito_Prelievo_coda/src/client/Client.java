package client;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client {
    
    public static void main(String[] args) {
        
        Hashtable<String,String> p = new Hashtable<String,String>();
        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.richiesta", "richiesta");
        p.put("queue.risposta", "risposta");

        try {

            Context jndiContext = new InitialContext(p);
            
            QueueConnectionFactory qcf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue reqQueue = (Queue) jndiContext.lookup("richiesta");
            Queue respQueue = (Queue) jndiContext.lookup("risposta");

            QueueConnection qc = qcf.createQueueConnection();
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueReceiver receiver = qs.createReceiver(respQueue);
            MyListener listener = new MyListener();
            receiver.setMessageListener(listener);

            Sender sender = new Sender(qs, reqQueue, respQueue);
            sender.run();

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
