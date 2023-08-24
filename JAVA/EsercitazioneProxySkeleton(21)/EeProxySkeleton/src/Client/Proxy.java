package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Servizio.IDispatcher;

public class Proxy implements IDispatcher{//Implementa l'interfaccia

    private Socket socket;//ho la socket passata, in e out che prendo dalla socket
    private DataInputStream in;
    private DataOutputStream out;

    public Proxy(){
        try{

            socket = new Socket("127.0.0.1", 3000);//ogni volta che creo un proxy, nuova socket mi prendo il collegamento!
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        }catch (UnknownHostException e){
            System.err.println("Errore Host Sconosciuto");
        }catch (IOException e){
            System.err.println("Errore, IO Exception!");
        }
    }
    
    @Override
    public void sendCmd(Integer s) { //metodo di Send!
        
        try{

            String mess = new String("send#"+s.toString()+"#"); //messaggio per la send

            out.writeUTF(mess); //invio del messaggio

            System.out.println("[Proxy] Messaggio inviato! Aspetto risposta...");

            String res = in.readUTF(); //aspetto risposta

            if(res.compareTo("ack")==0){
                System.out.println("Ack ricevuto"); //ricevo ack

            }else{
                System.err.println("Errore, non ho ricevuto ack!");
            }

        }catch(IOException e){
            System.err.println("Errore nel Proxy send");

        }
    }

    @Override
    public int getCmd() { //metodo di Get!

        int x=0;

        try {

            String mess = new String("get#"); //creo messaggio di get

            out.writeUTF(mess); //invio

            System.out.println("[Proxy] richiesta get inviata!");

            String res = in.readUTF(); //ricevo la risposta
            //e la converto in intero
            x = Integer.valueOf(res).intValue();
            
        } catch (IOException e) {
            System.out.println("Errore nella get");
        }

        return x; //ritorno valore!

    }
    
    
}
