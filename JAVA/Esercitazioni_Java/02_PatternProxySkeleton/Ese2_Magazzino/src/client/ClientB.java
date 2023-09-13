package client;

public class ClientB {

    public static void main(String[] args) {
        
        for(int i=0; i<5; i++){

            ThreadB t = new ThreadB();
            t.start();

        }
    }

}
