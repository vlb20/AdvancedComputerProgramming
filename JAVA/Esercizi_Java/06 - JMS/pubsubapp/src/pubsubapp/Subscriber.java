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
		
		//		jndi topic name   queue-name
		jndiProperties.put( "topic.test", "mytesttopic" );

        try {

            //1. Creazione Contesto iniziale
            Context jndiContext = new InitialContext(jndiProperties);
        
            //2. Lookup administered object, CONNECTIONFACTORY for TOPIC
            TopicConnectionFactory tcf = (TopicConnectionFactory)jndiContext.lookup("TopicConnectionFactory");
            //3. Lookup administered object, DESTINATION for TOPIC
            Topic topic = (Topic)jndiContext.lookup("test");

            //4. Creazione Connessione
            TopicConnection tc = tcf.createTopicConnection();

            /*
             * Nel caso di subscriber durable, prima di avviare la connessione
             * impostiamo un ID
             * tc.setClientID("clientDurable")
             */

            //4.1 START CONNESSIONE
            tc.start();
            //5. Creazione Sessione
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //6. Creazione Subscriber
            TopicSubscriber sub = ts.createSubscriber(topic);

            // se volessi creare un sub durable
			//TopicSubscriber sub = topicSession.createDurableSubscriber(topic, "DurableSub");

            TextMessage mess;

            do{
                System.out.println("In attesa di messaggi!");
                mess = (TextMessage)sub.receive();//8. Ricezione messaggi
                System.out.println(" + messaggio ricevuto: "+mess.getText());

            }while(mess.getText().compareTo("SEMPRE!")!=0);

            //9. Clean-up risorse
            sub.close();
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
