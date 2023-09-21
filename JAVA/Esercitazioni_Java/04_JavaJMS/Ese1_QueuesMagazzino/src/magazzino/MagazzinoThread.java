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
    private QueueConnection qc;

    public MagazzinoThread(Coda coda, MapMessage mm, QueueConnection qc){
        this.coda=coda;
        this.mm=mm;
        this.qc=qc;
    }

    public void run(){

        try {
            
            String op = mm.getString("operazione");
            int val = mm.getInt("valore");

            if(op.compareTo("deposita")==0){
            
                System.out.println("[MAGAZZINO-THREAD]: operazione = "+op+", valore= "+val);
                coda.inserisci(val);
            
            }else{//Operazione di prelievo
                //prelevo l'id e lo mando al client.
                //sfrutto getJMSReplyTo per rispondere al cleint
                System.out.println(" [MAGAZZINO-THREAD]: operazione = "+op);
                val = coda.preleva();

                /*
				 * Attenzione, la sessione la devo creare dentro il Thread perchè la session 
				 * è sempre single-threaded. Se creassi la sessione fuori il thread potrei
				 * avere problemi di concorrenza
            	*/

                QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                
                QueueSender sender = qs.createSender((Queue) mm.getJMSReplyTo());

                MapMessage reply = qs.createMapMessage();
                reply.setString("operazione", "risultato");
                reply.setInt("valore", val);

                sender.send(reply);

                sender.close();
                qs.close();

            }


        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
