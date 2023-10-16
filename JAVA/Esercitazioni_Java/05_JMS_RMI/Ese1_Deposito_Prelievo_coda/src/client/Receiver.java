package client;

public class Receiver extends Thread{

    private int id_articolo;

    public Receiver(int id_articolo) {

        this.id_articolo = id_articolo;
        
    }

    public void run(){

        System.out.println("[Receiver] Ho ricevuto l'id_articolo: "+id_articolo);

    }
    
}
