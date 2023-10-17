import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

public class DispatcherMsgListener implements MessageListener{

    private QueueSession qs;
    private Queue qresponse;
    private int port;

    public DispatcherMsgListener(QueueSession qs, Queue qresponse, int port) {
    
        this.qs = qs;
        this.qresponse = qresponse;
        this.port = port;

    }

    @Override
    public void onMessage(Message message) {
        
        TextMessage msg = (TextMessage) message;

        try {
            String mess = msg.getText();
            IDispatcher dispatcher = new DispatcherProxy("localhost", port, qs, qresponse);
            
            if(mess.equalsIgnoreCase("Preleva")){

                //Invoca il DispatcherProxy che deve invocare il servizio di prelievo
                System.out.println("[LISTENER] Ricevuta richiesta di prelievo");

                dispatcher.preleva();

            }else{

                //Deposita
                String[] splitted = mess.split("-");
                Integer valoreDaDepositare = Integer.valueOf(splitted[1]);
                System.out.println("[LISTENER] Ricevuta richiesta di deposito. Valore da depositare "+valoreDaDepositare);

                //Invoca il DispatcherProxy che deve invocare il servizio di deposito
                dispatcher.deposita(valoreDaDepositare);
            }
            
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
