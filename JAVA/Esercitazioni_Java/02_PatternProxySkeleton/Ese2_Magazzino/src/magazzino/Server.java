package magazzino;

import coda.CodaImpl;
import coda.CodaWrapperSem;
import servizi.ICoda;

public class Server{

    public static void main(String[] args) {
        
        ICoda laptops = new CodaImpl(5);
        ICoda smartphones = new CodaImpl(5);

        ICoda wrapper_l = new CodaWrapperSem(laptops);
        ICoda wrapper_s = new CodaWrapperSem(smartphones);

        ServerImpl magazzino = new ServerImpl(wrapper_l,wrapper_s);
        magazzino.runSkeleton();

    }
    
}
