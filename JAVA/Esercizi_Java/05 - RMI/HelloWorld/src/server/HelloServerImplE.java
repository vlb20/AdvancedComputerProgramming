package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.Hello;

//Implementazione per delega
public class HelloServerImplE extends UnicastRemoteObject implements Hello{

    protected HelloServerImplE() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("sayHello() invocato sul server...");
        return "Hello, world!";
    }
    


}
