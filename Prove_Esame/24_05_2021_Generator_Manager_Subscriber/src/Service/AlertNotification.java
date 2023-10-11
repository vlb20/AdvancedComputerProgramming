package Service;

import java.io.Serializable;
import java.util.Random;

public class AlertNotification implements Serializable{

    private int componentID;
    private int criticality;

    public AlertNotification(){
        Random rand = new Random();
        componentID = rand.nextInt(5)+1;
        criticality = rand.nextInt(3)+1;
    }
    
    public void setComponentID(int id) {
        componentID = id;
    }

    public int getComponentID() {
        return this.componentID;
    }

    public void setCriticality(int crit) {
        criticality = crit;
    }

    public int getCriticality() {
        return this.criticality;
    }
    
}
