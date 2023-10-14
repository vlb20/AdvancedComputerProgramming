package client;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Client {
    
    public static void main(String[] args) {
        
        if(args.length != 2){
            System.out.println("[CLIENT] Inserisci i parametri [ dato - port LoggerServer]");
            return;
        }

        if(Integer.parseInt(args[0])<0 || Integer.parseInt(args[0])>100){
            System.out.println("[CLIENT] Il dato inserito deve essere compreso ta 0 e 100");
        }

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
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher pub = ts.createPublisher(topic);

            MapMessage mm = ts.createMapMessage();
            mm.setInt("dato", Integer.parseInt(args[0]));
            mm.setInt("port", Integer.parseInt(args[1]));

            pub.send(mm);

            System.out.println("[CLIENT] Messaggio inviato sulla topic!");
            tc.close();
        
        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
