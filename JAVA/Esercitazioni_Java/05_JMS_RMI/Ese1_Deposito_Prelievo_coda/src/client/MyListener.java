package client;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MyListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        MapMessage mm = (MapMessage) message;
        try {
            int id_articolo = mm.getInt("id_articolo");
            Receiver receiver = new Receiver(id_articolo);
            receiver.start();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
