package Server;

public class Server {
    
    public static void main(String[] args){
        ServerImpl ser = new ServerImpl();
        ser.runSkeleton(); //uso il serverImpl per ereditarietà per attivare runSkeleton
    }
}
