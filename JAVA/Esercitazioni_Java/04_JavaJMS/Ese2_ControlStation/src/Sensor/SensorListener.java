package Sensor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import Coda.ICoda;

public class SensorListener implements MessageListener{

    private ICoda coda;

    public SensorListener(ICoda coda) {
        this.coda = coda;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage txm = (TextMessage) message;
        System.out.println("Ho ricevuto un messaggio!");

        try {

            String mess = txm.getText();
            System.out.println("Letto il messaggio: "+mess);
            TManager t = new TManager(coda, mess);
            t.start();

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
