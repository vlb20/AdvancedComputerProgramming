package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
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

public class Client {
    
    public static void main(String[] args) {
        
        if(args.length != 1){
            System.out.println("Inserisci il topic[math o text]");
            return;
        }

        Hashtable <String, String> p = new Hashtable<String, String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.math", "math");
        p.put("topic.text", "text");

        try {

            Context jndiContext = new InitialContext(p);
            
            TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic mathTopic = (Topic) jndiContext.lookup("math");
            Topic textTopic = (Topic) jndiContext.lookup("text");

            TopicConnection tc = tcf.createTopicConnection();
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            if(args[0].equalsIgnoreCase("math")){
                
                TopicPublisher pub = ts.createPublisher(mathTopic);
                for(int i=0; i<5; i++){

                    MapMessage mm = ts.createMapMessage();
                    Random rand = new Random();
                    int n1 = rand.nextInt(101);
                    int n2 = rand.nextInt(101);
                    mm.setInt("number1", n1);
                    mm.setInt("number2", n2);
                    pub.send(mm);
                    System.out.println("[Client] Invio il messaggio n°"+i+" con addendi: "+n1+" e "+n2);

                    Thread.sleep(2000);
                }
            
            }else if(args[0].equalsIgnoreCase("text")){
                
                TopicPublisher pub = ts.createPublisher(textTopic);
                for(int i=0; i<5; i++){

                    TextMessage txm = ts.createTextMessage();
                    Random rand = new Random();
                    int numb = rand.nextInt(101);
                    String mess = "save#"+numb;
                    txm.setText(mess);
                    pub.send(txm);
                    System.out.println("[Client] Invio il messaggio n°"+i+" con testo: "+mess);
                    
                    Thread.sleep(2000);
                }

            }else{
                System.err.println("Devi inserire come parametro o math o text!!!");
                return;
            }


        } catch (NamingException | JMSException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    }

}
