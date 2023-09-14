package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.MyService;

/*
 * InvokerClient agisce da client che invocherà prettamenete il metodo remoto
 * service_method() fornito dal servizio remoto MyService
 */

public class InvokerClient {
    
    public static void main(String[] args) {
        
        try{

            Registry rmiRegistry = LocateRegistry.getRegistry();
            MyService stub_myservice = (MyService)rmiRegistry.lookup("myservice");
            System.out.println("Ho ricevuto il riferimento a <myservice>");
            System.out.println(stub_myservice.toString());

            stub_myservice.service_method();
        
        }catch(RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

}
