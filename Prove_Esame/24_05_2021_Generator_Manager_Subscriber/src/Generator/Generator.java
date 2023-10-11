package Generator;

public class Generator {
    
    public static void main(String[] args) {
        
        for(int i=0; i<3; i++){

            GeneratorThread t = new GeneratorThread();
            t.start();

        }

    }

}
