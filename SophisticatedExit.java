import java.util.concurrent.TimeUnit;

public class SophisticatedExit {

    // ANSI Colors for the terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        // 1. Initialize the Shutdown Hook (The "Final Will")
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            performCleanup();
            System.out.println(RED + "\n[TERMINAL]: Goodbye, cruel world." + RESET);
        }));

        // 2. Simulate the program's lifecycle
        executeMainProcess();
    }

    private static void executeMainProcess() {
        System.out.println(CYAN + "Process ID: " + ProcessHandle.current().pid() + RESET);
        System.out.println("Status: RUNNING. Press Ctrl+C to initiate termination.");
        
        try {
            while (true) {
                // Keep the CPU idling until an interrupt signal is received
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            // Signal received
        }
    }

    private static void performCleanup() {
        String[] tasks = {"Closing Data Streams", "Purging Memory Buffers", "Syncing I/O"};
        
        System.out.println("\n" + CYAN + "--- INITIATING SYSTEM EXIT ---" + RESET);
        for (String task : tasks) {
            try {
                System.out.print("Task: " + task + "...");
                Thread.sleep(600); // Simulate processing latency
                System.out.println(" [OK]");
            } catch (InterruptedException ignored) {}
        }
    }
}
