package magazzino;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;

import coda.Coda;

public class MagazzinoListener implements MessageListener{

    private Coda coda;
    private QueueConnection qc;

    public MagazzinoListener(Coda coda, QueueConnection qc){

        this.coda=coda;
        this.qc=qc;

    }

    @Override
    public void onMessage(Message message) {

        MapMessage mm = (MapMessage)message;

        MagazzinoThread mt = new MagazzinoThread(coda,mm,qc);
        mt.start();

    }



}
