import java.io.*;
import java.util.*;

public class TestScanner {

    public static void main(String[] args) {
        if( args.length == 0 ) {
            System.out.println("Usage: TestScanner <filename>");
            System.exit(-1);
        }
        Scanner s = new Scanner(args[0]).useDelimiter("\\s*=\\s*");
        
       
        // File format is 
        // name = string
        // age = int
        while(s.hasNext()) {
            String name = s.next();
            int age = s.nextInt();
            System.out.println(name + " Age: " + age + "");
        }
        
    }
}
