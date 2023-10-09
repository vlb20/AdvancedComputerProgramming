package Client;

import java.util.Hashtable;
import java.util.Random;

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
    
    public static void main(String[] args){

        if(args.length < 1){
            System.out.println("Inserisci il tipo di richiesta (temperature or pressure)");
            return;
        }

        Hashtable <String, String> p = new Hashtable <String, String>();
		p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.data", "mydata");

        try {

            Context jndiContext = new InitialContext(p);

            //Lookup administered object
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) jndiContext.lookup("data");

            TopicConnection tc = tcf.createTopicConnection();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher pub = ts.createPublisher(topic);

            MapMessage mm = ts.createMapMessage();

            for(int i=0; i<20; i++){

                if(args[0].equalsIgnoreCase("temperature")){

                    mm.setString("type", "temperature");
                    Random rand = new Random();
                    int randValue = rand.nextInt(101);
                    mm.setInt("value", randValue);

                    pub.send(mm);
                    System.out.println("[CLIENT] Richiesta temperatura con valore: "+randValue);
                
                }else if(args[0].equalsIgnoreCase("pressure")){

                    mm.setString("type", "pressure");
                    Random rand = new Random();
                    int randValue = rand.nextInt(51)+1000;
                    mm.setInt("value", randValue);

                    pub.send(mm);
                    System.out.println("[CLIENT] Richiesta pressione con valore: "+randValue);

                }else{
                    System.out.println("Tipo di richiesta errato - Messaggio non inviato");
                }

                Thread.sleep(2000);

            }
        
        } catch (NamingException e) {
            System.err.println("Naming Exception");
        } catch (JMSException e) {
            System.err.println("JMS Exception");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        

    }

}
