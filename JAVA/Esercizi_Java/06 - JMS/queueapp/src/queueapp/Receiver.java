package queueapp;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Receiver {

    public static void main(String[] args){

        Hashtable<String, String> prop = new Hashtable<String, String> ();
		
		prop.put( "java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory" );
		prop.put( "java.naming.provider.url", "tcp://127.0.0.1:61616" );
		
		//		jndi queue name   queue-name
		prop.put( "queue.test", "mytestqueue" );

        try {

            //1. Creazione contesto iniziale
            Context jndiContext = new InitialContext(prop);
            
            //2. Lookup administered objects, CONNECTIONFACTORY per QUEUE
            QueueConnectionFactory qcf = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            
            //3. Lookup administered objects, DESTINATION per QUEUE
            Queue queue = (Queue)jndiContext.lookup("test"); // il prefisso "queue." non fa parte del nome jndi


            //4. Creazione Connessione
            QueueConnection qc = qcf.createQueueConnection();
            //4.1 Avvio Connessione (Differenza dal Sender)
            qc.start();

            //5. Creazione Sessione
            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //6. Creazione Receiver
            QueueReceiver receiver = qs.createReceiver(queue);

            //7. Ricezione messaggio
            TextMessage message;

            do{

                System.out.println("In attesa di messaggi!");
                message = (TextMessage) receiver.receive();//bloccante
                System.out.println(" + messaggio ricevuto: "+message.getText());

            }while(message.getText().compareTo("SEMPRE!!!") != 0);

            //8. Clean-up
            receiver.close();
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
