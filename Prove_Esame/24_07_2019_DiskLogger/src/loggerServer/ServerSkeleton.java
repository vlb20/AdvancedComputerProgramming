package loggerServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import service.ILogger;

public abstract class ServerSkeleton implements ILogger{
    
    private int port;

    public ServerSkeleton(int port){
        this.port = port;
    }

    public void runSkeleton(){

        try {

            DatagramSocket socket = new DatagramSocket(port);
        
            while (true) {
                byte[] data = new byte[66508];
                DatagramPacket pack = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), port);
                socket.receive(pack);
                String message = new String(pack.getData(), 0, pack.getLength());
                int dato = Integer.parseInt(message);
                ServerThread t = new ServerThread(dato,this);
                t.start();

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
