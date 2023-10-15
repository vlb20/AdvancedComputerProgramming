package Coda;

public abstract class CodeWrapper implements ICoda{
    
    protected ICoda c;

    public CodeWrapper(ICoda c){
        this.c = c;
    }

    public int size(){
        return c.size();
    }

    public boolean empty(){
        return c.empty();
    }

    public boolean full(){
        return c.empty();
    }

}
