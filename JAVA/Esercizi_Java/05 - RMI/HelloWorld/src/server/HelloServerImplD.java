package server;

import java.rmi.RemoteException;

import service.Hello;

//implementazione per delega
public class HelloServerImplD implements Hello{

    public HelloServerImplD(){}

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("sayHello() invocato sul server...");
        return "Hello, world!";
    }
    

}
