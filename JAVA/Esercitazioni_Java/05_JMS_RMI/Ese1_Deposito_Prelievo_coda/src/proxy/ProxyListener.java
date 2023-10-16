package proxy;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueSession;

public class ProxyListener implements MessageListener{

    private QueueSession qs;

    public ProxyListener(QueueSession qs) {
        this.qs = qs;
    }

    @Override
    public void onMessage(Message message) {
        
        MapMessage mm = (MapMessage) message;
        Receiver receiver = new Receiver(qs, mm);
        receiver.start();
        

    }
    
}
