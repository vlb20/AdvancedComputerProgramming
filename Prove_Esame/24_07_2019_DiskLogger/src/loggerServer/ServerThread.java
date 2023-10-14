package loggerServer;

import service.ILogger;

public class ServerThread extends Thread{

    private int dato;
    private ILogger logger;

    public ServerThread(int dato, ILogger l) {

        this.dato = dato;
        logger = l;

    }

    public void run(){
        System.out.println("[SERVER-THREAD] Il dato ricevuto è: "+dato);
        logger.registraDato(dato);
    }
    
}
