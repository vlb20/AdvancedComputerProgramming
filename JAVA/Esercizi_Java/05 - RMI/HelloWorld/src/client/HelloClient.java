package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.Hello;

public class HelloClient {

    private HelloClient(){}
    
    public static void main(String[] args) {
        
        //String host = (args.length < 1) ? null : args[0];

        try {
            // ottiene il riferimento all'oggetto Hello remoto
            Registry registry = LocateRegistry.getRegistry();
            Hello stub = (Hello) registry.lookup("Hello");

            //invocazione del metodo remoto sayHello()
            String response = stub.sayHello();
            System.out.println("Risposta: "+response);

        } catch (RemoteException e) {
            System.err.println("Client Remote exception: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("Client Not Bound exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
