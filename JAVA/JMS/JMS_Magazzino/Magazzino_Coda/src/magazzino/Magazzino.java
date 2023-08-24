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
import codaimpl.CodaCircolare;
import codaimpl.CodaWrapperSynchr;

public class Magazzino {
    
    public static void main(String[] args){

        //dobbiamo attivare il servizio JNDI
            //dove prendere il CONTESTO INIZIALE
            //dove andare a CONTATTARE IL PROVIDERE ACTIVEMQ
        Hashtable <String, String> p = new Hashtable<String, String>(); //proprietà

        //contesto iniziale
        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); //nome della proprietà che fa riferimento al servizio dei nomi nel factory
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        /*
         * Se non mettiamo questa parte non possiamo
         * attaccarci al servizio di naming
         * fornito da ActiveMQ
         * --------------------
         */

        //ora BINDING tra nome nel contesto JNDI e nome nel contesto reale Active MQ
            //dobbiamo settare i nomi delle CODE
        p.put("queue.Richiesta", "Richiesta");
        //coda.NomeCoda
        //l'altra coda non ci interessa perchè sfruttiamo l'header JMSReplyTo
    
        //ora dobbiamo CREARCI il NOSTRO CONTESTO
            //le eccezioni che possiamo avere sono quelle
            //relative al naming, al contesto, alla sessione e connessione
                //dunque l'IDE ci chiederà di gestirle
        try {
            Context ctx = new InitialContext(p);

            //dobbiamo prendere gli ADMINISTRATED OBJECTS
            QueueConnectionFactory qconnf = (QueueConnectionFactory)ctx.lookup("QueueConnectionFactory");
            
            /*
			 * Magazzino attende sulla coda 'Richiesta'; MagazzinoThread
			 * invia i messagi di risposta sulla coda 'Risposta'
			 */

            //definiamo la destinazione
            Queue queueRequest = (Queue) ctx.lookup("Richiesta");

            //Creiamo la connection
            QueueConnection qconn = qconnf.createQueueConnection();
            //Start connection
            qconn.start(); //lo devo fare perchè sono nel message consumer

            //Creo la sessione
            QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creata la sessione devo creare il RECEIVER
            QueueReceiver qreceiver = qsession.createReceiver(queueRequest);

            /*
             * Non abbiamo fatto il lookup della coda
             * di risposta perchè utilizziamo l'header
             * ReplyTo che sarà settato dai client
             */

            /*
                possiamo implementare la ricezione asincrona con i listener
                lato magazzino possiamo specificarlo
                devo implementare una classe MagazzinoListner
                che estende il messageListner di JMS
                e fare l'override di onMessage
            */

            /*
			 * Receive asincrona: il costruttore di MagazzinoListener
			 * riceve coda e qconn
			 *
			 */
			
			/*
				 * Attenzione, la sessione la devo creare dentro il Thread perchè la session 
				 * è sempre single-threaded. Se creassi la sessione fuori il thread potrei
				 * avere problemi di concorrenza. Per questo motivo passo la QueueConnection al Listener
            */
            
            //DEFINIAMO LE CODE
            Coda coda = new CodaWrapperSynchr(new CodaCircolare(10));

            //Creo il listener
            MagazzinoListener l = new MagazzinoListener(coda, qconn);

            //setto il listener
            qreceiver.setMessageListener(l);
            
            
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
    }
}
