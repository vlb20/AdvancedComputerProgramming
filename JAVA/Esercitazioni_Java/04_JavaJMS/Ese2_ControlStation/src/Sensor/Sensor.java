package Sensor;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Coda.CodaImpl;
import Coda.CodaWrapperLock;
import Coda.ICoda;

public class Sensor {
    
    public static void main(String[] args){

        ICoda coda = new CodaImpl(5);
        CodaWrapperLock wrapperLock = new CodaWrapperLock(coda);

        Hashtable<String, String> p = new Hashtable<String,String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.commands", "commands");

        try {

            Context jndiContext = new InitialContext(p);
            
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) jndiContext.lookup("commands");

            TopicConnection tc = tcf.createTopicConnection();
            tc.start();

            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber sub = ts.createSubscriber(topic);
            SensorListener listener = new SensorListener(wrapperLock);
            sub.setMessageListener(listener);
            System.out.println("Sensor pronto!");
            TExecutor t = new TExecutor(wrapperLock);
            t.start();

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
