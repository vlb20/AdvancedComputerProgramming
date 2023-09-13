package service;

public interface IDispatcher {//interfaccia di esposizione del servizio!
    
    public void sendCmd(int command);
    public int getCmd();

}
