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

public class Client {

    public static void main(String[] args){

        Hashtable <String, String> p = new Hashtable<String, String>(); //proprietà

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); //nome della proprietà che fa riferimento al servizio dei nomi nel factory
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.Richiesta", "Richiesta");
        p.put("queue.Risposta", "Risposta");
            //creiamo una nuova coda, un nuovo bind

        Context ctx;
        try {
            ctx = new InitialContext(p);

            QueueConnectionFactory qconnf = (QueueConnectionFactory)ctx.lookup("QueueConnectionFactory");
        
            Queue queueRequest = (Queue) ctx.lookup("Richiesta");
            Queue queueResponse = (Queue) ctx.lookup("Risposta"); //coda risposta

            QueueConnection qconn = qconnf.createQueueConnection();
            qconn.start();


            QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //----RECEIVER----//
            QueueReceiver qreceiver = qsession.createReceiver(queueResponse);
                //Creiamo un ClientListener che riceve i
                //messaggi in maniera asincrona dal magazzino
            ClientListener listener = new ClientListener();

            //----SENDER----//
            QueueSender qsender = qsession.createSender(queueRequest);
                //devo creare il messaggio ed impostare l'header
            MapMessage message = qsession.createMapMessage();

            for(int i=0; i<10; i++){
                
                if(Math.random()<0.5){
                    //al 50% faccio deposito altrimenti prelevo

                    //DEPOSITO
                    message.setString("operazione", "deposita");
                    Random r = new Random();
                    int randomvalue = r.nextInt(100);

                    message.setInt("valore", randomvalue);

                    // SETTARE il JMSReplyTo
                    message.setJMSReplyTo(queueResponse); //coda su cui aspettare la risposta

                    //invio messaggio
                    qsender.send(message);
                    System.out.println("[CLIENT] inviato messaggio");

                }else{

                    //PRELIEVO
                    message.setString("operazione", "preleva");
                    message.setJMSReplyTo(queueResponse);
                    qsender.send(message);
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
