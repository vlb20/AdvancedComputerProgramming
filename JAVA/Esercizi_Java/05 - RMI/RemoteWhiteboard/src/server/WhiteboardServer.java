package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import whiteboard.Whiteboard;

public class WhiteboardServer {
    
    public static void main(String[] args) {
        
        //creazione e registrazione dell'oggetto remoto

        System.out.println("Creando l'oggetto...");
        
        try {

            Whiteboard board = new WhiteboardImpl();
            
            System.out.println(board.toString()+"\n");

            Registry rmiRegistry = LocateRegistry.getRegistry();
            rmiRegistry.rebind("myWhiteboard", board);
            System.out.println("Oggetto registrato con il nome <myWhiteboard>\n");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
