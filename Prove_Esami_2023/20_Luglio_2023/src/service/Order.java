package service;

import java.io.Serializable;
import java.util.Random;

public class Order implements Serializable{
    
    private int id;
    private int location;
    private String address;

    public Order(){

        Random rand = new Random();
        id = rand.nextInt(100)+1;
        location = rand.nextInt(5)+1;
        int valueAddr = rand.nextInt(6)+4;
        address = valueAddr + "th avenue";

    }

    public int getId(){
        return this.id;
    }

    public int getLocation(){
        return this.location;
    }

    public String getAddress(){
        return this.address;
    }

}
