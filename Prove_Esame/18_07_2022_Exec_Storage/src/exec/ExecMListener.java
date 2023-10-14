package exec;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import service.IStorage;

public class ExecMListener implements MessageListener{

    private IStorage storage;

    public ExecMListener(IStorage storage){
        this.storage = storage;
    }

    @Override
    public void onMessage(Message message) {
        
        MapMessage mm = (MapMessage) message;
        try {
        
            int n1 = mm.getInt("number1");
            int n2 = mm.getInt("number2");
            int prod = n1*n2;

            ExecMThread t = new ExecMThread(storage, prod);
            t.start();

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
    
}
