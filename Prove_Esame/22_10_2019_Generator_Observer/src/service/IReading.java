package service;

import java.io.Serializable;

public interface IReading extends Serializable{
    
    public void setTipo(String tipo);
    public String getTipo();
    public void setValore(int value);
    public int getValore();

}
