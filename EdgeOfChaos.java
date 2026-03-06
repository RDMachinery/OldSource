import java.util.Random;

public class EdgeOfChaos {
    // ADJUST THIS: 0.1 (Frozen), 0.3-0.5 (Edge of Chaos), 0.8 (Chaos)
    private static final double LAMBDA = 0.45; 
    
    private static final int WIDTH = 80;
    private static final int STEPS = 40;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int[] cells = new int[WIDTH];
        
        // Seed the center with a single "living" cell to observe information flow
        cells[WIDTH / 2] = 1;

        System.out.println("Simulating Lambda: " + LAMBDA);
        for (int i = 0; i < STEPS; i++) {
            printCells(cells);
            cells = evolve(cells);
        }
    }

    private static int[] evolve(int[] current) {
        int[] next = new int[WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            // Look at left neighbor, self, and right neighbor (periodic boundaries)
            int left = current[(i - 1 + WIDTH) % WIDTH];
            int self = current[i];
            int right = current[(i + 1) % WIDTH];
            
            int sum = left + self + right;

            // Langton's logic: Lambda determines the probability of a cell being "active"
            // based on the input sum.
            if (sum > 0) {
                next[i] = (rand.nextDouble() < LAMBDA) ? 1 : 0;
            } else {
                next[i] = 0;
            }
        }
        return next;
    }

    private static void printCells(int[] cells) {
        StringBuilder sb = new StringBuilder();
        for (int cell : cells) {
            sb.append(cell == 1 ? "█" : " ");
        }
        System.out.println(sb.toString());
    }
}

