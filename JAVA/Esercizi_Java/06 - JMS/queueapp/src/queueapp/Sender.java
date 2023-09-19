package queueapp;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender {

    public static void main(String[] args){

        Hashtable<String, String> prop = new Hashtable<String, String> ();
		
		prop.put( "java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory" );
		prop.put( "java.naming.provider.url", "tcp://127.0.0.1:61616" );

        //jndi queue name   queue-name
        prop.put("queue.test", "mytestqueue");

        try {

            //1. Creazione contesto iniziale
            Context jndiContext = new InitialContext(prop);
            
            //2. Lookup administered objects, CONNECTIONFACTORY per QUEUE
            QueueConnectionFactory qcf = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");

            //3. Lookup administered objects, DESTINATION per QUEUE
            Queue queue = (Queue)jndiContext.lookup("test"); // il prefisso "queue." non fa parte del nome jndi


            //4. Creo la Connection
            QueueConnection qc = qcf.createQueueConnection();

            //5. Creo la Session(single-threaded)
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //6. Creo il Sender ed il messaggio
            QueueSender sender = qs.createSender(queue);
            TextMessage message = qs.createTextMessage(null);

            //7. Invio messaggi
            for(int i=0; i<5; i++){
                message.setText("Forza Napoli ["+i+"] !");
                sender.send(message);
            }

            message.setText("SEMPRE!!!");
            sender.send(message);

            System.out.println("I messaggi di incitazione al Napoli sono stati inviati!");

            //8. Clean-up risorse
            sender.close();
            qs.close();
            qc.close();
        
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
