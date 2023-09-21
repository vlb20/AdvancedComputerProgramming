package pubsub;

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

public class Publisher{

    public static void main(String[] args){

        //TOPIC - MESSAGE.
        //Soccer Italy-wins (as input argument)
        if(args.length < 2){
            return;
        }

        Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put("java.naming.provider.url","tcp://127.0.0.1:61616");
		properties.put("topic.soccer" , "soccernews");
		properties.put("topic.tennis" , "tennisnews");

        try {

            Context jndiContext = new InitialContext(properties);
        
            TopicConnectionFactory tcf = (TopicConnectionFactory)jndiContext.lookup("TopicConnectionFactory");
            Topic soccer = (Topic)jndiContext.lookup("soccer");
            Topic tennis = (Topic)jndiContext.lookup("tennis");

            TopicConnection tc = tcf.createTopicConnection();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            
            //Seleziona il correct Topic
            Topic selectedTopic;
            if(args[0].equalsIgnoreCase("soccer")){
                selectedTopic = soccer;
            }else if(args[0].equalsIgnoreCase("tennis")){
                selectedTopic = tennis;
            }else{
                System.out.println("unknown topic");
                return;
            }

            //Sending the message
            TopicPublisher publisher = ts.createPublisher(selectedTopic);
            TextMessage mess = ts.createTextMessage(args[1]);
            mess.setIntProperty("propInt", 10); //can be used by possible message selectors

            publisher.publish(mess);

            System.out.println("I've published the messagge "+args[1]);

            //Some attributes of the message
            System.out.println("\nMessage id " + mess.getJMSMessageID());
            System.out.println("Message id " + mess.getJMSCorrelationID()); // not set
            System.out.println("Message id " + mess.getJMSDeliveryMode()); //PERSISTENT, NON-PERSISTENT
            System.out.println("Message id " + mess.getJMSExpiration());
            System.out.println("Message id " + mess.getJMSPriority());
            System.out.println("Message id " + mess.getJMSReplyTo());

            //Clean-up risorse
            publisher.close();
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