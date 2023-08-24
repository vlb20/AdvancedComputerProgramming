package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import Servizio.IDispatcher;

public class WorkerS extends Thread{
    
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private IDispatcher skeleton;
    
    public WorkerS(Socket c, IDispatcher sk){

        //prendo la socket e l'elemento con cui posso fare upcall
        client = c;
        skeleton = sk;

            try{
                in = new DataInputStream(client.getInputStream()); //prendo gli stream!
                out = new DataOutputStream(client.getOutputStream());
            
            }catch (IOException e){
                System.err.println("Errore nel prendere gli stream");
            }
    }

    @Override
    public void run(){

        //devo riconoscere il metodo richiesto!!!
        try {
            
            String mess = in.readUTF();
            StringTokenizer tokens = new StringTokenizer(mess, "#");

            String method = tokens.nextToken();

            //faccio il check del messaggio letto!
            if(method.compareTo("send")==0){
                Integer value = Integer.valueOf(tokens.nextToken()).intValue();
                //prelevo il valore da inserire

                skeleton.sendCmd(value);

                System.out.println("[WorkerS] Elemento scritto!");
                //inserisco
                //mando messaggio di ack
                String ok = new String("ack");

                out.writeUTF(ok);
                System.out.println("Messaggio ack inviato!");

            }else if(method.compareTo("get")==0){

                Integer value = skeleton.getCmd();

                System.out.println("[WorkerS] Comando preso!");

                //creo risposta!
                String res = value.toString();

                //Invio la risposta!
                out.writeUTF(res);
            }

        } catch (IOException e) {
            System.err.println("Errore nella lettura!");
        }
    }

}
