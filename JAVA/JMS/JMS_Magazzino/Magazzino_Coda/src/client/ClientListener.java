package client;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class ClientListener implements MessageListener {

    /*
     * Il Client fa solo una stampa
     */
    @Override
    public void onMessage(Message message) {
        //prendiamo il messaggio
        MapMessage msg = (MapMessage) message;
        //e lo stampiamo
        try {
            System.out.println("[CLIENT-Listener] messaggio ricevuto - valore: "+msg.getInt("valore"));
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
