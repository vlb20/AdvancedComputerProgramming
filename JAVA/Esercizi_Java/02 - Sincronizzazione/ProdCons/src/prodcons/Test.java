package prodcons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    
    public static void main(String[] args) {
        
        //creazione del buffer condiviso
        Buffer buf = new Buffer();

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int choice=0;
        int id = 1;

        while(true){
            //Scegli 0 per un consumatore e 1 per un produttore
            System.out.println("0 (C)/ 1 (P) >> ");

            try {
                //Input da tastiera
                choice = Integer.parseInt(stdin.readLine());

            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if( choice == 0){
                Consumer c = new Consumer(buf, " consumatore_"+id);
                c.start();
            }
            else{
                Producer p = new Producer(buf, " produttore_"+id);
                p.start();
            }

            id=id+1;
        }
    }
}
