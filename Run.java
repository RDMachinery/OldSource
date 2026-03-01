import java.util.Random;

/**
 * This class generates a secure password.
 *
 * @author Mario Gianota (gianotamario@gmail.com) 9 March 2021
 */
public class Run {

    private int minPasswordLength = 16;     // Make this as long as possible
    private static final char[] lowercase = {'a','b','c','d','e','f','g','h','i','j','k','l',
            'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] uppercase = {'A','B','C','D','E','F','G','H','I','J','K','L',
            'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    private Random rnd = new Random();

    public String getPassword() {
        StringBuffer password = new StringBuffer();
        for(int i=0; i<minPasswordLength; i++) {
            int r = (int)(rnd.nextDouble()*25)+1;
            double switchCase = rnd.nextDouble();
            if( switchCase < 0.5 ) {
                password.append(lowercase[r]);
            } else if( switchCase >= 0.5) {
                password.append(uppercase[r]);
            }
        }
        return password.toString();
    }
    public static void main(String[] args) {
        System.out.println("Password: " + (new Run().getPassword()));
    }
}
