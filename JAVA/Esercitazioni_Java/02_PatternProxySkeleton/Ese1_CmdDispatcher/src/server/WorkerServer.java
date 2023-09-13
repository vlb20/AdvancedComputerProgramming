package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import service.IDispatcher;

public class WorkerServer extends Thread{

    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private IDispatcher skeleton;

    public WorkerServer(Socket socket, IDispatcher sk) {

        //prendo la socket e l'elemento con cui posso fare upcall
        client=socket;
        skeleton=sk;

        try {
            
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){

        //devo riconoscere il metodo richiesto!

        try {
            String method = in.readUTF();
            int x;

            //faccio il check del messaggio letto
            if(method.compareTo("sendCmd")==0){
                //prelevo il valore da inserire
                x=in.readInt();
                
                System.out.println ("	[WorkerServer] method: " + method + ", " + x);
                
                skeleton.sendCmd(x);//inserisco

                System.out.println("[WorkerServer] Elemento scritto!");

                //mando messaggio di ack
                String ok = new String("ack");

                out.writeUTF(ok);
                System.out.println("Messaggio ack inviato!");
                
            }else if(method.compareTo("getCmd")==0){

                System.out.println ("	[WorkerServer] method: " + method + ", -");

                x = skeleton.getCmd();

                out.writeInt(x);
            
            }else{
                System.out.println("Error: unknown method!");
            }

            System.out.println();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
