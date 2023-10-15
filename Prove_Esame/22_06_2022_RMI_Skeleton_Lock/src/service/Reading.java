package service;

import java.io.Serializable;
import java.util.Random;

public class Reading implements Serializable{

    private String type;
    private int data;

    public Reading(){

        Random rand = new Random();
        data = rand.nextInt(50)+1;
        if(data%2==0){
            type = "temperature";
        }else{
            type = "pressure";
        }

    }

    public String getType(){
        return this.type;
    }

    public int getData(){
        return this.data;
    }

}
