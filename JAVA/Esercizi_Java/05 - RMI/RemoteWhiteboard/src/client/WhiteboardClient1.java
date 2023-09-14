package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import whiteboard.Shape;
import whiteboard.Whiteboard;

public class WhiteboardClient1 {
    
    public static void main(String[] args) {
        
        try {

            //1. ottengo il riferimento alla lavagna remota
            Registry rmiRegistry = LocateRegistry.getRegistry();
            Whiteboard board = (Whiteboard)rmiRegistry.lookup("myWhiteboard");
            System.out.println("Ho preso il riferimento < myWhiteboard > \n" + board.toString());

            //Aggiunge alla lavagna remota 4 Shape selezionati in maniera casuale
            //tra Triangoli e Quadrati ogni 10 secondi

            Shape s;
            int x;

            for(int i=0; i<4; i++){

                x = (int)(1+Math.random()*10);

                if(x<=5){
                    s=new Triangle();
                }else{
                    s=new Square();
                }

                System.out.println("\nAggiungendo la forma ("+x+") "+s.toString() );

                //invocazione remota. L'argomento Shpe è un oggetto non-remot
                board.addShape(s);

                Thread.sleep(10000);

            }
    
        } catch (RemoteException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
