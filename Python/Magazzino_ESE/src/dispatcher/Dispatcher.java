package dispatcher;

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

public class Dispatcher{
    public static void main(String[] args) {
        Hashtable<String, String> p = new Hashtable<String, String>();
        p.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        p.put("queue.request","request");
        p.put("queue.response","response");

        try {
            Context jndiContext = new InitialContext(p);
            QueueConnectionFactory qcf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            QueueConnection qc = qcf.createQueueConnection();
            Queue request = (Queue) jndiContext.lookup("request");
            Queue response = (Queue) jndiContext.lookup("response");
            qc.start();
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver qr = qs.createReceiver(request);
            MyListener l = new MyListener(qs,response);
            qr.setMessageListener(l);
            System.out.println("Dispatcher Pronto!");
            
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}