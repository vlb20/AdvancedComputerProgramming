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

public class Dispatcher {
    
    public static void main(String[] args) {
        
        /*
		 * Il dispatcher crea una destination di coda richieste per parlare con un client Python
		 * Una volta ricevuta la richiesta (asincrona tramite MessageListener) di operazione parlerà tramite Proxy-Skeleton (socket)
		 * verso il server
		 */

        if(args.length != 1){
            System.out.println("Inserisci il port number come paramtro");
            return;
        }

        Hashtable<String,String> p = new Hashtable<String,String>();
        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.request", "request");
        p.put("queue.response", "response");

        try {

            Context jndiContext = new InitialContext(p);
            
            QueueConnectionFactory qcf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");

            //Creo la coda di richieste
            Queue qrequest = (Queue)jndiContext.lookup("request");
            QueueConnection qc = qcf.createQueueConnection();
            qc.start();

            QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver receiver = qs.createReceiver(qrequest);
            
            //Creo la coda di risposte
            Queue qresponse = (Queue)jndiContext.lookup("response");

            //passo al listener la sessione, la coda di risposte ed il numero di porto in modo da inviare il messaggio al server
            int port = Integer.parseInt(args[0]);
            DispatcherMsgListener listener = new DispatcherMsgListener(qs, qresponse, port);
            receiver.setMessageListener(listener);

            System.out.println("Dispatcher avviato!");

        } catch (NamingException | JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
