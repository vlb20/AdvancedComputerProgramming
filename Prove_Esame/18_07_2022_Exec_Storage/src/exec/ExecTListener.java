package exec;

import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import service.IStorage;

public class ExecTListener implements MessageListener{

    private IStorage storage;
    
    public ExecTListener(IStorage storage) {
        
        this.storage = storage;

    }

    @Override
    public void onMessage(Message message) {
        
        TextMessage txm = (TextMessage) message;
        try {

            String m = txm.getText();
            
            StringTokenizer st = new StringTokenizer(m, "#");
            String numberPart = " ";
            for(int i=0; i<2; i++){
                numberPart = st.nextToken();
            }

            int result = Integer.parseInt(numberPart);
            System.out.println("[ExecT] Ho ricevuto il messaggio save#"+result);

            ExecTThread t = new ExecTThread(storage, result);
            t.start();

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }

}
