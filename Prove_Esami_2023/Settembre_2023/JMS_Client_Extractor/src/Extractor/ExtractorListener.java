package Extractor;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

public class ExtractorListener implements MessageListener{

    private TopicSession ts;
    private TopicPublisher pubTemp;
    private TopicPublisher pubPress;

    public ExtractorListener(TopicSession ts, TopicPublisher pubTemp, TopicPublisher pubPress) {

        this.ts = ts;
        this.pubTemp = pubTemp;
        this.pubPress = pubPress;

    }

    @Override
    public void onMessage(Message message) {
        
        MapMessage mm = (MapMessage)message;

        try {

            String type = mm.getString("type");
            int value = mm.getInt("value");

            TextMessage tm = ts.createTextMessage();
            tm.setText(String.valueOf(value));

            if(type.equals("temperature")){

                pubTemp.send(tm);
                System.out.println("[EXTRACTOR] Invio messaggio sulla coda temp con valore:"+value);

            }else{

                pubPress.send(tm);
                System.out.println("[EXTRACTOR] Invio messaggio sulla coda press con valore:"+value);

            }
        
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
