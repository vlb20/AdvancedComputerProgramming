import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedOutputStream;

//Legge una stringa da System.in e fa un output sulla pipe
public class WriterThread extends Thread{
    
    private DataOutput dataOut;

    public WriterThread(PipedOutputStream pipeOut){

        dataOut = new DataOutputStream(pipeOut);

    }

    public void run(){

        //BufferedReader per la lettura da System.in
        BufferedReader keyboardBuf = new BufferedReader(new InputStreamReader(System.in));
        String s;

        while(true){
            try {
                System.out.println("[Writer] inserisci la stringa: ");
                
                //lettura stringa
                s=keyboardBuf.readLine();
                System.out.println("[Writer] stringa: < "+s+" > in output sulla pipe" );

                //output su pipe
                dataOut.writeUTF(s);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
