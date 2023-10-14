package disk;

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

public class Disk {
    
    public static void main(String[] args){

        Hashtable<String, String> p = new Hashtable<String, String>();
        p.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        p.put("topic.storage","storage");

        try {

            Context jndiContext = new InitialContext(p);
            
            //Lookup administered objects
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) jndiContext.lookup("storage");

            TopicConnection tc = tcf.createTopicConnection();
            tc.start();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber sub = ts.createSubscriber(topic);
            DiskListener listener = new DiskListener();

            sub.setMessageListener(listener);
            System.out.println("[DISK] Aspettando messaggi...");

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

}
