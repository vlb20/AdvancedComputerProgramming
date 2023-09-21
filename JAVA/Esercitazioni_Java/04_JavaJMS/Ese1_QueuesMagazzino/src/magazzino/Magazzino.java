package magazzino;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import coda.Coda;
import coda.CodaCircolare;
import coda.CodaWrapperLock;

public class Magazzino {
    
    public static void main(String[] args){

        Hashtable <String, String> p = new Hashtable <String, String>();
		p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.Richiesta", "Richiesta");

        try {

            Context jndiContext = new InitialContext(p);
            
            QueueConnectionFactory qcf = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            Queue queueRequest = (Queue)jndiContext.lookup("Richiesta");

            QueueConnection qc = qcf.createQueueConnection();
            qc.start();

            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueReceiver receiver = qs.createReceiver(queueRequest);

            //Creazione coda circolare per memorizzare gli id articolo
            Coda coda = new CodaWrapperLock(new CodaCircolare(5));

            /*
				 * Attenzione, la sessione la devo creare dentro il Thread perchè la session 
				 * è sempre single-threaded. Se creassi la sessione fuori il thread potrei
				 * avere problemi di concorrenza. Per questo motivo passo la QueueConnection al Listener
            */

            MagazzinoListener l = new MagazzinoListener(coda,qc);
            receiver.setMessageListener(l);

            System.out.println("[MAGAZZINO] Server avviato");

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
