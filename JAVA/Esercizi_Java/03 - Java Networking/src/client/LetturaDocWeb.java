package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LetturaDocWeb {
    
    public static void main(String[] args) {
        
        try {
            
            URL page = new URL("http://www.scuolapsb.unina.it/");
            URLConnection conn = page.openConnection();

            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = reader.readLine();
            while(line != null){
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
