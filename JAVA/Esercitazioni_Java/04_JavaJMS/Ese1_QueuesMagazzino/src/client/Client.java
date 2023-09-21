package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client{

    private static final int N = 10;

    public static void main(String[] args) {
        
        Hashtable <String, String> p = new Hashtable <String, String>();
		p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.Richiesta", "Richiesta");
        p.put("queue.Risposta", "Risposta");

        try {

            Context jndiContext = new InitialContext(p);
            
            //Lookup administered objects
            QueueConnectionFactory qcf = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            Queue queueRequest = (Queue)jndiContext.lookup("Richiesta");
            Queue queueResponse = (Queue)jndiContext.lookup("Risposta");

            //Create Connection
            QueueConnection qc = qcf.createQueueConnection();
            qc.start(); //perchè in questo caso si comporta sia da sender che da receiver

            QueueSession qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Receiver per la coda di messaggi Risposta
            QueueReceiver receiver = qs.createReceiver(queueResponse);
            ClientListener listener = new ClientListener();
            receiver.setMessageListener(listener);

            //Sender per la coda di messaggi Richiesta
            QueueSender sender = qs.createSender(queueRequest);

            //MapMessage per creare messaggi del tipo
            // key-value (operazione: tipo_operazione) e (id_articolo: valore)
            MapMessage message = qs.createMapMessage();

            //Generazione random e ciclica delle richieste
            for(int i=0; i<N; i++){

                if(Math.random() < 0.5){

                    //CASO DEPOSITA
                    message.setString("operazione", "deposita");
                    Random r = new Random();
                    int randomValue = r.nextInt(100);
                    message.setInt("valore", randomValue);

                    message.setJMSReplyTo(queueResponse);//inserisco qua la destinazione di dove voglio la risposta

                    sender.send(message);
                    System.out.println("[CLIENT] Inviato messaggio DEPOSITA con valore: "+randomValue);

                }else{
                    //CASO PRELEVA
                    message.setString("operazione", "preleva");

                    message.setJMSReplyTo(queueResponse);

                    sender.send(message);
                    System.out.println("[CLIENt] Mandato messaggio preleva");
                }

            }

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}