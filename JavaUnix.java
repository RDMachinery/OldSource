package org.example;

import java.util.*;
import java.io.*;

public class JavaUnix {
    // Virtual File System (VFS)
    private static Map<String, String> fileSystem = new HashMap<>();
    private static String currentDirectory = "/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Java-UNIX Kernel Initialized.");
        System.out.println("Type 'exit' to halt system.");

        while (true) {
            System.out.print("user@java-unix:" + currentDirectory + "$ ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            dispatch(input);
        }
        scanner.close();
    }

    // The Dispatcher (Simulating the System Call interface)
    private static void dispatch(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        switch (command) {
            case "ls":
                fileSystem.keySet().forEach(System.out::println);
                break;
            case "touch":
                if (parts.length > 1) {
                    File f = new File(parts[1]);
                    try {
                        f.createNewFile();
                        fileSystem.put(parts[1], "");
                    } catch(java.io.IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                break;
            case "echo":
                // Basic redirection simulation: echo "text" > filename
                if (input.contains(">")) {
                    String content = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
                    String fileName = parts[parts.length - 1];
                    fileSystem.put(fileName, content);
                } else {
                    System.out.println(input.replace("echo ", ""));
                }
                break;
            case "cat":
                if (parts.length > 1) {
                    System.out.println(fileSystem.getOrDefault(parts[1], "File not found."));
                }
                break;
            case "ps":
                simulateProcessList();
                break;
            default:
                System.out.println("sh: command not found: " + command);
        }
    }

    // Simulating Multi-threading (UNIX Processes)
    private static void simulateProcessList() {
        System.out.println("PID\tCOMMAND\t\tSTATUS");
        System.out.println("0\tKernel\t\tRunning");
        System.out.println("1\tInit\t\tSleeping");
        System.out.println(Thread.activeCount() + "\tJVM_Shell\tRunning");
    }
}