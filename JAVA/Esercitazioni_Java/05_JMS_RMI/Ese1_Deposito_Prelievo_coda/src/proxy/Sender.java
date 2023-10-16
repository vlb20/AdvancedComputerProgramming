package proxy;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

public class Sender extends Thread{

    private int id_articolo;
    private QueueSession qs;
    private MapMessage mm;

    public Sender(int id_articolo, QueueSession qs, MapMessage mm) {
        this.id_articolo = id_articolo;
        this.qs = qs;
        this.mm = mm;
    }

    public void run(){

        try {

            QueueSender sender = qs.createSender((Queue) mm.getJMSReplyTo());
            MapMessage mess = qs.createMapMessage();
            mess.setInt("id_articolo", id_articolo);
            sender.send(mess);
            System.out.println("[Sender] Inviato messaggio al client con id_articolo: "+id_articolo);
            
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
