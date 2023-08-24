package magazzino;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;

import coda.Coda;

public class MagazzinoListener implements MessageListener{
	
		//la classe deve avere 2 attributi per schedulare il nuovo thread
		//andrà ad operare sulla coda e sull'oggetto che mi permetterà di creare la session 
		//dunque l'oggetto connection
		//poi gli deve passare il messaggi che deve gestire

		private Coda coda;
		private QueueConnection qconn;

		public MagazzinoListener(Coda coda, QueueConnection qconn){
				
				this.coda = coda;
				this.qconn = qconn;
		}

        @Override //quando riceviamo il messaggio:
        public void onMessage(Message message) {
            //devo prendere il messaggio ricevuto
			//e passarlo al thread che lo deve gestire
			
			//devo fare il typecasting del messaggio
			//TIPOMESSAGGIO m = (TIPOMESSAGGIO) message
			MapMessage mm = (MapMessage) message;

			//creo il thread magazzino e gli passo coda, qconn e message
            MagazzinoThread mt = new MagazzinoThread(coda, mm, qconn);
			//avvio il thread
            mt.start();

            //il thread farà la ricezione e invio della risposta

            /*QUESTO E' TUTTO CIO'CHE FACCIO LATO RICEZIONE */
        }
}