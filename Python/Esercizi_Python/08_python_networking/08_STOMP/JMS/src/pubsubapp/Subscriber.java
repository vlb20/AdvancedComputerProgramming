package pubsubapp;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Subscriber {
    
    public static void main(String[] args){

        Hashtable<String, String> jndiProperties = new Hashtable<String, String>();

        jndiProperties.put( "java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory" );
		jndiProperties.put( "java.naming.provider.url", "tcp://127.0.0.1:61616" );
		
		jndiProperties.put( "topic.test", "mytesttopic" );

        try {
            
            Context jndiContext = new InitialContext(jndiProperties);
            
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic)jndiContext.lookup("test");
            
            TopicConnection tc = tcf.createTopicConnection();
            tc.start();

            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber sub = ts.createSubscriber(topic);
            TextMessage msg;

            do{
                System.out.println("In attesa di messaggi!");
                msg=(TextMessage)sub.receive();
                System.out.println(" + messaggio ricevuto: "+msg.getText());
            }while(msg.getText().compareTo("fine") != 0);

            sub.close();
            ts.close();
            tc.close();

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
