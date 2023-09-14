package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import whiteboard.Observer;
import whiteboard.Shape;
import whiteboard.Whiteboard;

public class WhiteboardImpl extends UnicastRemoteObject implements Whiteboard{

    private int count;
    private Vector<Shape> boardContent; //vettore di shape disegnate sulla lavagna
    private Vector<Observer> attachedObservers; //contiene gli observer registrati

    protected final static long serialVersionUID = 10;

    protected WhiteboardImpl() throws RemoteException {
        
        count=0;
        boardContent = new Vector<Shape>();
        attachedObservers = new Vector<Observer>();

    }

    @Override
    public void addShape(Shape s) throws RemoteException {

        count = count+1;
        System.out.println("\nAggiungendo la forma #"+count+" "+s.toString());

        s.draw();

        boardContent.add(s);

        notifyAllObservers();

    }

    private void notifyAllObservers() {

        System.out.println("(Nuova forma, notifico gli observers!)");
        int size = attachedObservers.size();

        for(int i=0; i<size; i++){
            
            try {
                attachedObservers.get(i).observerNotify();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void attachObserver(Observer observer) throws RemoteException {

        System.out.println("\nNuovo observer registrato! \n"+observer.toString());
        attachedObservers.add(observer);

    }

    @Override
    public Vector<Shape> getShapes() throws RemoteException {
        return boardContent;
    }

}