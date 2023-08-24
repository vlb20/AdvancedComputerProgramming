package magazzino;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import coda.Coda;

public class MagazzinoThread extends Thread{
    
    private Coda coda;
    private MapMessage mm;
    private QueueConnection qconn;

    public MagazzinoThread(Coda c, MapMessage m, QueueConnection qc){

        this.coda = c;
        this.mm = m;
        this. qconn = qc;
    }

    //dobbiamo ridefinire il metodo run()
    public void run(){
        //dobbiamo capire il tipo di operazione
            //ed eventualmente il valore

        //devo fare il parsing del messaggio
        //la nostra operazione è una stringa
        //il nostro valore è un intero
            //devo sfruttare la tabella associativa
        try {
            String op = mm.getString("operazione");

            if(op.compareTo("deposita") == 0){
                int val = mm.getInt("valore");
                System.out.println("[MAGAZZINO-THREAD] operazione: "+op+" val");

                //INSERIMENTO
                coda.inserisci(val);

            }else {

                //PRELIEVO
                int val = coda.preleva();

                //creo un sender per inviare un messaggio
                    //mi serve una sessione
                    //la creo sfruttando il qconn
                QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                //dalla sessione mi creo il sender
                    //prendo il sender sulla coda su cui voglio inviare la risposta
                QueueSender qsender = qsession.createSender((Queue) mm.getJMSReplyTo());
                /*
                 * Nel caso normale Receiver si prende l'oggetto coda creato con il lookup
                 * In questo caso sfrutto questo campo che conterrà il riferimento
                 * all'oggetto remoto.
                 * Sfrutto quindi l'header dei messaggi JMS
                 * Lato client quindi dobbiamo fare il SetJMSReplyTO.
                 */

                //una volta creato il sender devo costruirmi il mess di risposta
                    //conterrà l'id numerico del prodotto
                MapMessage reply = qsession.createMapMessage();
                reply.setString("operazione", "risultato"); //Questa è fake -> giusto per riempimento
                reply.setInt("valore", val);

                //impostato il messaggio, devo inviarlo
                qsender.send(reply);

                qsender.close();;
                qsession.close();

                //la connection non la chiudiamo mai, visto che il magazzino è vivo per sempre

                /*
                 * Possiamo creare fuori la sessione
                 * Ma potremmo avere problemi di sincronizzazione tra i vari thread
                 */
            }

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
