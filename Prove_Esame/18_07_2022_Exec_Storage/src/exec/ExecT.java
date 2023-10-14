package exec;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

import service.IStorage;

public class ExecT {
    
    public static void main(String[] args) {
        
        Hashtable <String, String> p = new Hashtable<String, String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.text", "text");

        try {

            Context jndiContext = new InitialContext(p);
            
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic texTopic = (Topic) jndiContext.lookup("text");

            TopicConnection tc = tcf.createTopicConnection();
            tc.start();

            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IStorage storage = (IStorage) rmiRegistry.lookup("myStorage");

            TopicSubscriber sub = ts.createSubscriber(texTopic);
            ExecTListener listener = new ExecTListener(storage);

            System.out.println("[ExecT] In attesa di messaggi...");
            sub.setMessageListener(listener);
        
        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
