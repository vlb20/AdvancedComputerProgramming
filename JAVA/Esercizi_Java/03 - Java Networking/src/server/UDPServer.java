package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    
    public static void main(String[] args) {
        
        try {

            /*
             * Creazione socket e pacchetto UDP lato server
             */

            DatagramSocket socket = new DatagramSocket(8050);
            
            /*
             * Se forniamo una dimensione del buffer troppo piccola
             *l a dimensione eccedente del pacchetto andrà persa
             */
            byte buffer[]= new byte[65508];//65508 e’ la max dimensione di un pacchetto UDP
            DatagramPacket pkt = new DatagramPacket(buffer, buffer.length);

            System.out.println("[Server]: attesa pacchetto UDP...");
            socket.receive(pkt); //chiamata bloccante
            System.out.println("[Server]: pacchetto ricevuto.");

            /*
			 * Lettura e stampa del contenuto del pacchetto
			 * NOTA: l'invocazione di .getLength restituisce la lunghezza
			 * effettiva del pacchetto.
			 */

            byte[] receivedData = pkt.getData();

            String s = new String(receivedData, 0, pkt.getLength());
            System.out.println("[Server]: contenuto pacchetto: "+s);

            /*
             * Invio risposta al client
             */

            s = "OK client, paccheto ricevuto...Forza Napoli Sempre!";

            /*
			 * NOTA: nella costruzione del pacchetto 'response', l'IP / porta del 
			 * destinatario (ossia il client) sono ottenuti dal pacchetto 'packet'
			 */
            DatagramPacket response = new DatagramPacket(s.getBytes(), s.getBytes().length, pkt.getAddress(), pkt.getPort());

            Thread.sleep(5000);

            System.out.println("[Server]: invio pacchetto risposta...");
            socket.send(response);
            System.out.println("[Server]: pacchetto inviato.");

            //chiusura socket
            socket.close();


        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
