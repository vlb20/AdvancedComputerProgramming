package whiteboard;

import java.io.Serializable;

/*
 * extends Serializable per consentire il trasferimento
 * di oggetti non-remoti Shape come argomenti di una invocazione
 * RMI -> se non lo mettiamo avremo un'eccezione sulle operaziini di mashalling
 */
public interface Shape extends Serializable{
    
    public void draw();

}
