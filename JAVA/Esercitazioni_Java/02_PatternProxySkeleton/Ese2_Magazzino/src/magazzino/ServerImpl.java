package magazzino;

import servizi.ICoda;

public class ServerImpl extends ServerSkeleton{

    public ICoda smartphones;
    public ICoda laptops;

    public ServerImpl(ICoda smartphones, ICoda laptops){
        this.smartphones=smartphones;
        this.laptops=laptops;
    }

    @Override
    public void deposita(String tipo, int id) {
        if(tipo.equalsIgnoreCase("laptop")){
        
            laptops.deposita(id);
            System.out.println("Depositato laptop con id: "+id);
        
        }else if(tipo.equalsIgnoreCase("smartphone")){

            smartphones.deposita(id);
            System.out.println("Depositato smartphone con id: "+id);

        }else{

            System.out.println("Tipologia articolo errata!");

        }
    }

    @Override
    public int preleva(String articolo) {
        
        int value = 0;

        if(articolo.equalsIgnoreCase("smartphone")){

            value=smartphones.preleva();
            System.out.println("Smartphone prelevato con id: "+value);
        
        }else if(articolo.equalsIgnoreCase("laptop")){

            value=laptops.preleva();
            System.out.println("Laptop prelevato con id: "+value);

        }else{

            System.out.println("Tipologia articolo errata!");

        }

        return value;

    }
    
}
