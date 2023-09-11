package codaimpl;

import coda.Coda;

/*
 * implementazione della coda circolare --> lock free
 */

public class CodaCircolare implements Coda{

    private int data[]; //gli elementi sono memorizzati in un array gestito in maniera circolare

    private int size;
    private int elem; //numero di elementi

    private int tail;
    private int head;

    public CodaCircolare(int s){
        size=s;
        elem=0;
        data = new int[size];
        tail=head=0;
    }

    //metodi di utilità

    @Override
    public boolean empty() {
        if(elem == 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean full() {
        if(elem == size){
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    //Inserisce un elemento
    @Override
    public void inserisci(int i) {

        data[tail%size]=i;//inserimento in coda

        try {
            Thread.sleep(101 + (int)(Math.random()*100));//sleep di durata random max pari a 200ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elem=elem+1;//numero di elementi + 1
        System.out.println("inserito "+i+" (tot = "+elem+" )");
        tail=tail+1;//aggiorno l'indice della tail
    }

    //Estrazione di un elemento
    @Override
    public int preleva() {
        
        int x = data[head%size]; //estrazione in testa

        try {
            Thread.sleep(101 + (int)(Math.random()*400));//sleep di durata random max pari a 500ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elem=elem-1; //numero di elementi-1
        System.out.println("prelevato "+x+" (tot = "+elem+" )");

        head=head+1;//aggiorno l'indice della testa
        return x;
    }

    


    
}
