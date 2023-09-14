package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import printer.PrinterSkeleton;

public interface IDispatcher extends Remote{
    
    public boolean printRequest(String docName) throws RemoteException;

    public void addPrinter(PrinterSkeleton printer) throws RemoteException;

}
