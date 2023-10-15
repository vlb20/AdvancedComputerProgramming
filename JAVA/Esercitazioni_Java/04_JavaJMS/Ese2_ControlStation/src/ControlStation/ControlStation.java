package ControlStation;

import java.util.Hashtable;
import java.util.Random;

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

public class ControlStation {
    
    public static void main(String[] args) {
        
        Hashtable<String, String> p = new Hashtable<String,String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.commands", "commands");

        try {

            Context jndiContext = new InitialContext(p);
            
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) jndiContext.lookup("commands");
            
            TopicConnection tc = tcf.createTopicConnection();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher pub = ts.createPublisher(topic);
            
            int N=20;
            Random rand = new Random();

            for(int i=0; i<N; i++){

                int randint = rand.nextInt(3)+1;
                String command = " ";

                if(randint == 1){

                    command = "startSensor";

                }else if(randint == 2){

                    command = "stopSensor";

                }else{

                    command = "read";

                }

                TextMessage txm = ts.createTextMessage(command);
                pub.send(txm);

            }

            pub.close();
            ts.close();
            tc.close();

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
