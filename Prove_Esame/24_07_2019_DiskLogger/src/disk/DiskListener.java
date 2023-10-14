package disk;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class DiskListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        MapMessage mm = (MapMessage)message;
        
        DiskThread t = new DiskThread(mm);
        t.start();

    }
    
}
