package pubsubapp;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Publisher {
    
    public static void main(String[] args){

        Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		
		jndiProperties.put( "java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory" );
		jndiProperties.put( "java.naming.provider.url", "tcp://127.0.0.1:61616" );

        jndiProperties.put("topic.test", "mytesttopic");

        try {

            Context jndiContext = new InitialContext(jndiProperties);

            //Lookup Administered Objects
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic)jndiContext.lookup("test");

            TopicConnection tc = tcf.createTopicConnection();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher pub = ts.createPublisher(topic);
            TextMessage text = ts.createTextMessage();

            for(int i=0; i<5; i++){
                text.setText("Forza Napoli [" + i + "]");
                pub.publish(text);
            }

            text.setText("fine");
            pub.send(text);

            System.out.println("I messaggi sono stati inviati");

            //clean up risorse
            pub.close();
            ts.close();
            tc.close();
            
        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
