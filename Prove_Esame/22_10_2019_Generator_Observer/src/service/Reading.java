package service;

import java.util.Random;

public class Reading implements IReading{

    private String tipo;
    private int valore;

    public Reading(){
        Random rand = new Random();
        valore = rand.nextInt(50);
        if(rand.nextInt(100)%2==0){
            tipo="temperatura";
        }else{
            tipo="pressione";
        }
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setValore(int value) {
        this.valore = value;
    }

    @Override
    public int getValore() {
        return valore;
    }
    
}
