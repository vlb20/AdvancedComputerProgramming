package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import service.IController;
import service.Reading;

public class ControllerImpl extends UnicastRemoteObject implements IController{

    private Vector<ActuatorProxy> actuators;

    public ControllerImpl() throws RemoteException{
        actuators = new Vector<ActuatorProxy>();
    }

    @Override
    public boolean sensorRead(Reading reading) throws RemoteException {
        
        boolean esito = false;

        String type = reading.getType();
        int data = reading.getData();
        String attributes = type+" "+data;

        for(int i=0; i<actuators.size(); i++){
            esito = actuators.get(i).execute(attributes);
            if(esito == true){
                return esito;
            }
        }

        return esito;

    }

    @Override
    public void addActuator(int port) throws RemoteException {
        ActuatorProxy actuator = new ActuatorProxy(port);
        actuators.add(actuator);
        System.out.println("Richiesta di attach di un Actuator sul port: "+port);
    }
    
}
