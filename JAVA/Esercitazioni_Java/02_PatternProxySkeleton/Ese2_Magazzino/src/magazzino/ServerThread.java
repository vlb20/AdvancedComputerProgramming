package magazzino;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import servizi.IMagazzino;

public class ServerThread extends Thread{
    
    private IMagazzino skeleton;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public ServerThread(Socket socket, IMagazzino magazzino){
        this.socket=socket;
        this.skeleton=magazzino;

        try {

            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){

        //devo riconoscere il metodo richiesto
        String method;
        try {

            method = dataIn.readUTF();
            
            if(method.compareTo("deposita")==0){

                String articolo = dataIn.readUTF();
                int id = dataIn.readInt();
                System.out.println("[MAGAZZINO THREAD] Deposito: "+articolo+" con ID: "+id);
                skeleton.deposita(articolo, id);
            
            }else if(method.compareTo("preleva")==0){

                String articolo = dataIn.readUTF();

                FileOutputStream fileOut;
                PrintStream outStream;

                if(articolo.equalsIgnoreCase("laptop")){
                
                    fileOut = new FileOutputStream("./file1.txt");
                    outStream = new PrintStream(fileOut);

                    int id = skeleton.preleva(articolo);
                    System.out.println("[MAGAZZINO THREAD] Prelevo "+articolo+" con ID: "+id);

                    dataOut.writeInt(id);

                    outStream.append("id = "+id);

                    dataOut.flush();
                
                }else if(articolo.equalsIgnoreCase("smartphone")){

                    fileOut = new FileOutputStream("./file2.txt");
                    outStream = new PrintStream(fileOut);

                    int id = skeleton.preleva(articolo);
                    System.out.println("[MAGAZZINO THREAD] Prelevo "+articolo+" con ID: "+id);

                    dataOut.writeInt(id);

                    outStream.append("id = "+id);

                    dataOut.flush();

                }else{

                    System.out.println("Articolo errato!");

                }

                

                
            
            }else{

                System.out.println("Metodo errato!");

            }

            dataIn.close();
            socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
