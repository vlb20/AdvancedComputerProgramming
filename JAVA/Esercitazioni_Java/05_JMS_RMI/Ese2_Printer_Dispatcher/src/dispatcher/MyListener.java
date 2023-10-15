package dispatcher;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MyListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        System.out.println("[MyListener] messaggio ricevuto, avvio il thread!");
        MapMessage mm = (MapMessage) message;
        TDispatcher t = new TDispatcher(mm);
        t.start();
    }
    
}
