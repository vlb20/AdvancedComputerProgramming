package services;

import java.io.Serializable;

public interface IPrinter extends Serializable{
    
    public boolean print(String docName);

}
