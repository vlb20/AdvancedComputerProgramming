package sensorsManager;

public class SensorManager {
    
    public static void main(String[] args) {
        
        for(int i=0; i<10; i++){

            SensorT t = new SensorT();
            t.start();
            
        }

    }

}
