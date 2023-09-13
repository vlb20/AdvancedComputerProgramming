package client;

public class ClientA {
    
    public static void main(String[] args) {
        
        for(int i=0; i<5; i++){
            
            ThreadA t = new ThreadA();
            t.start();

        }

    }

}
