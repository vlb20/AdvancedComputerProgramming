import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ProvaListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        TextMessage tm = (TextMessage)message;

        try {
            System.out.println("Valore: "+tm.getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
