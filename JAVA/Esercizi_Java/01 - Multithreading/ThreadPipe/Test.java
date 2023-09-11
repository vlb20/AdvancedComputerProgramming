import java.io.PipedOutputStream;

public class Test {

    public static void main(String[] args) {
        
        //Un piped output stream può essere connesso ad
        //un piped input stream per creare una pipe di comunicazioni
        PipedOutputStream pipeOut = new PipedOutputStream();

        WriterThread w = new WriterThread(pipeOut);
        ReaderThread r = new ReaderThread(pipeOut);

        w.start();
        r.start();
    }
}
