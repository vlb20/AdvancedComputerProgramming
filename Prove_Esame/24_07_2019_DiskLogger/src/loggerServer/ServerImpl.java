package loggerServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerImpl extends ServerSkeleton{

    private int port;

    public ServerImpl(int port) {
        
        super(port);

    }

    @Override
    public synchronized void registraDato(int dato) {
        System.out.println("[SERVER] Il dato registrato è: "+dato);
        try {
        
            FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write("Saved:"+dato+"\n");
            pw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
