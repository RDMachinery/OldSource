public class ReversibleAdder {
    long sum;
    long inputA;

    public ReversibleAdder(long sum, long inputA) {
        this.sum = sum;
        this.inputA = inputA;
    }
    public ReversibleAdder() {}

    /**
     * Forward Computation: A + B = C
     * We preserve 'a' so that the information is never erased.
     */
    public ReversibleAdder compute(long a, long b) {
        long result = a + b;
        return new ReversibleAdder(result, a);
    }

    /**
     * Backward Computation (Reversal)
     * Using the Sum and the preserved Witness (inputA),
     * we reconstruct the hidden input.
     */
    public void reverse(ReversibleAdder state) {
        long recoveredB = state.sum - state.inputA;

        System.out.println("--- Reversing Computation ---");
        System.out.println("Result was: " + state.sum);
        System.out.println("Recovered Input A: " + state.inputA);
        System.out.println("Recovered Input B: " + recoveredB);
    }

    public static void main(String[] args) {
        ReversibleAdder adder = new ReversibleAdder();

        // 1. Perform Forward Logic
        ReversibleAdder result = adder.compute(45, 55);
        System.out.println("Forward: " + result);

        // 2. Run the Machine Backwards
        // Even if we "forgot" the original 55, the state holds the truth.
        adder.reverse(result);
    }
}