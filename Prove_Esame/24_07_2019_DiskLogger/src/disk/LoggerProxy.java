package disk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import service.ILogger;

public class LoggerProxy implements ILogger{

    private int port;

    public LoggerProxy(int port){

        this.port = port;

    }

    @Override
    public void registraDato(int dato) {

        try {

            DatagramSocket sock = new DatagramSocket();
            String s = String.valueOf(dato);
            DatagramPacket pack = new DatagramPacket(s.getBytes(), s.length(), InetAddress.getLocalHost(), port);
            sock.send(pack);
            sock.close();

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
