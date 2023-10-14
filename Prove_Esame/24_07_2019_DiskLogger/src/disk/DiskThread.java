package disk;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class DiskThread extends Thread{

    private int dato;
    private int port;

    public DiskThread(MapMessage mm) {

        try {

            dato = mm.getInt("dato");
            port = mm.getInt("port");

            System.out.println("[DISK-THREAD] Dato da salvare: "+dato);
            
            LoggerProxy proxy = new LoggerProxy(port);
            proxy.registraDato(dato);

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
