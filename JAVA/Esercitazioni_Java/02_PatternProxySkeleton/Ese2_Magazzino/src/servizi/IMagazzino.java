package servizi;

public interface IMagazzino {
    
    public void deposita(String tipo, int id);
    public int preleva(String articolo);

}
