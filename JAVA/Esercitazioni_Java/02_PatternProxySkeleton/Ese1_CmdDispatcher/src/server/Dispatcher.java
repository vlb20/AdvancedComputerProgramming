package server;

public class Dispatcher {
    
    public static void main(String[] args) {

        DispatcherImpl ser = new DispatcherImpl();
        ser.runSkeleton(); //uso il serverImpl per ereditarietà per attivare sunSkeleton
    }

}
