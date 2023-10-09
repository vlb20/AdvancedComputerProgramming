package Extractor;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Extractor {
    
    public static void main(String[] args){

        Hashtable <String, String> p = new Hashtable <String, String>();
		p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.data", "mydata");
        p.put("topic.temp", "mytemp");
        p.put("topic.press","mypress");

        try {
            
            Context jndiContext = new InitialContext(p);
            
            //Lookup administered objects
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topicData = (Topic) jndiContext.lookup("data");
            Topic topicTemp = (Topic) jndiContext.lookup("temp");
            Topic topicPress = (Topic) jndiContext.lookup("press");

            TopicConnection tc = tcf.createTopicConnection();
            tc.start();

            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber subData = ts.createSubscriber(topicData);
            TopicPublisher pubTemp = ts.createPublisher(topicTemp);
            TopicPublisher pubPress = ts.createPublisher(topicPress);

            ExtractorListener listener = new ExtractorListener(ts, pubTemp, pubPress);
            subData.setMessageListener(listener);


        } catch (NamingException e) {
            System.err.println("NamingException in Extractor");
        } catch (JMSException e) {
            System.err.println("JMSException in Extractor");
        }
    
    }

}
