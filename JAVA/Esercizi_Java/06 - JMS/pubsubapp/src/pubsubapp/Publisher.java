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

        //		jndi topic name   queue-name
		jndiProperties.put( "topic.test", "mytesttopic" );

        try {

            //1. Creazione Contesto iniziale
            Context jndiContext = new InitialContext(jndiProperties);
        
            //2. Lookup administered object, CONNECTIONFACTORY for TOPIC
            TopicConnectionFactory tcf = (TopicConnectionFactory)jndiContext.lookup("TopicConnectionFactory");
            //3. Lookup administerd object, DESTINATION for TOPIC
            Topic topic = (Topic)jndiContext.lookup("test");

            //4. Creazione connessione
            TopicConnection tc = tcf.createTopicConnection();
            //5. Creazione sessione
            TopicSession ts = (TopicSession) tc.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //6. Creazione Publisher
            TopicPublisher pub = ts.createPublisher(topic);

            //7. Creazione messaggio da pubblicare
            TextMessage mess = ts.createTextMessage();

            for(int i=0; i<5; i++){
                mess.setText("Forza Napoli ["+i+"]");
                pub.publish(mess);//8. Pubblicazione messaggio
            }

            mess.setText("SEMPRE!");
            pub.send(mess);

            System.out.println("I messaggi sono stati inviati!");

            //9. Clean-up risorse
            pub.close();
            ts.close();
            tc.close();



        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
