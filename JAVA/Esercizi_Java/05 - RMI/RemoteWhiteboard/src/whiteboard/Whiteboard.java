package whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/*
 * interface Whiteboard, metodi della lavagna elettronica remota
 */

public interface Whiteboard extends Remote{
    
    public void addShape(Shape s) throws RemoteException;

    //Metodo per consentire l'attache di un observer sul subject
    public void attachObserver(Observer observer) throws RemoteException;

    /*
	 * getShapes() ritorna un vector di Shape. Tale vector viene popolato
	 * ogni qual volta si invoca addShape(...)
	 */
    public Vector<Shape> getShapes() throws RemoteException;

}
