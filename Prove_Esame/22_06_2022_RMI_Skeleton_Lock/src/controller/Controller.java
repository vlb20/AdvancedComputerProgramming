package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IController;

public class Controller {
    
    public static void main(String[] args) {
        
        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IController controller = new ControllerImpl();
            rmiRegistry.rebind("myController", controller);
            System.out.println("[Controller] Rebind effettuato!");
        
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
