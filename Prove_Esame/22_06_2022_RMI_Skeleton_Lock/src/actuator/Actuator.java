package actuator;

public class Actuator {
    
    public static void main(String[] args) {
        
        if(args.length != 2){
            System.out.println("Inserisci i parametri [porta e filename]");
            return;
        }

        ActuatorImpl actuatorImpl = new ActuatorImpl(Integer.parseInt(args[0]), args[1]);
        actuatorImpl.runSkeleton();
        System.out.println("[Actuator] pronto!");
    }

}
