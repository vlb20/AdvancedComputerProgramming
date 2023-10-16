package client;

import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

public class Sender extends Thread{

    private QueueSession qs;
    private Queue reqQueue;
    private Queue respQueue;

    public Sender(QueueSession qs, Queue reqQueue, Queue respQueue) {
        
        this.qs=qs;
        this.reqQueue=reqQueue;
        this.respQueue=respQueue;

    }

    public void run(){

        try {

            QueueSender sender = qs.createSender(reqQueue);
            MapMessage mm = qs.createMapMessage();
            mm.setJMSReplyTo(respQueue);
            for(int i=0; i<10; i++){
                Random rand = new Random();
                int id_articolo = rand.nextInt(100);

                if(id_articolo%2 ==0){
                    mm.setString("tipoRichiesta", "deposita");
                    mm.setInt("id_articolo", id_articolo);
                    System.out.println("[Sender] Invio richiesta di deposito dell'articolo "+id_articolo);
                }else{
                    mm.setString("tipoRichiesta", "preleva");
                    System.out.println("[Sender] Invio richiesta di prelievo");
                }

                sender.send(mm);
            }
        
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
    

}
