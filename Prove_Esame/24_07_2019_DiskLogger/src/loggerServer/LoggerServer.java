package loggerServer;

public class LoggerServer {
    
    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("[LoggerServer] Devi inserire il port come parametro!");
        }

        ServerImpl logger = new ServerImpl(Integer.parseInt(args[0]));
        System.out.println("[SERVER] Pronto!");
        logger.runSkeleton();
        

    }

}
