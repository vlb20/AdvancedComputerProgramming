package printer;

public class PrinterServer {
    
    public static void main(String[] args) {
        
        if(args.length!=1){
            System.out.println("Devi inserire il portnumber");
            return;
        }

        PrinterSkeleton skel = new PrinterSkeleton(Integer.parseInt(args[0]));
        skel.runSkeleton();

    }

}
