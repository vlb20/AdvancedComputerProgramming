package pubsub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener{

    @Override
    public void onMessage(Message arg0) {
        TextMessage mess = (TextMessage)arg0;
        System.out.println("Ricevendo messaggi...");

        try {
            String msg = mess.getText();
            System.out.println("Sono il subscriber. Leggo il seguente messaggio ->"+msg);
            System.out.println("La int property del messaggio è: "+arg0.getIntProperty("propInt"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    


}
