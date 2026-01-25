import java.util.Scanner;

/**
 * The Rogue Logic Engine
 * Built on the Laws of Identity, Non-Contradiction, and Excluded Middle.
 */
public class RogueLogic {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- 1D LOGIC ENGINE ACTIVATED ---");
        System.out.print("Enter state for Proposition A (true/false): ");
        boolean a = scanner.nextBoolean();
        
        System.out.print("Enter state for Proposition B (true/false): ");
        boolean b = scanner.nextBoolean();

        System.out.println("\n--- VALIDATING CLASSICAL LAWS ---");
        
        // 1. Law of Identity: A is A
        System.out.println("Identity [A == A]: " + (a == a));

        // 2. Law of Non-Contradiction: A and NOT A is always false
        // (We check if it's impossible for both to be true)
        boolean contradiction = a && !a;
        System.out.println("Non-Contradiction [!(A && !A)]: " + !contradiction);

        // 3. Law of Excluded Middle: A or NOT A is always true
        boolean excludedMiddle = a || !a;
        System.out.println("Excluded Middle [A || !A]: " + excludedMiddle);

        System.out.println("\n--- EMERGENT OPERATIONS ---");
        
        // XOR: The 'Exclusive Choice' - True only if A and B differ
        boolean xor = a ^ b;
        System.out.println("XOR (Difference): " + xor);

        // NAND: The Universal Gate (Wolfram would approve)
        // If you have enough NAND gates, you can build a universe.
        boolean nand = !(a && b);
        System.out.println("NAND (Universal): " + nand);

        System.out.println("\n--- SYSTEM TERMINATED ---");
    }
