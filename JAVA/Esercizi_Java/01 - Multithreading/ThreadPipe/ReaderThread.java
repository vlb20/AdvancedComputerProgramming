
//Il thread legge il contenuto dalla pipe

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ReaderThread extends Thread{
    
    private DataInputStream dataIn;

    public ReaderThread( PipedOutputStream pipeOut){
        try {
            
            //Crea un PipedInputStram collegato al PipedOutputStream
            //utilizzato in output dal thread Writer

            dataIn = new DataInputStream( new PipedInputStream(pipeOut));

        } catch (IOExceptionException e) {
            e.printStackTrace();
        }
        
    }

    public void run(){
        
        String s;

        while( true ){

            try {
                System.out.println(" [READER] aspettando un nuovo input...");
                //input da pipe
                s = dataIn.readUTF();
                System.out.println(" [READER] input dalla pipe: "+s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
