import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RoomMap {
    private static final int MAP_SIZE = 10;

    private static Map<Integer, Room> rooms;
    private static int currentPlayerPosition;

    public static void main(String[] args) {
        initializeMap();

        currentPlayerPosition = 1; // Start player at room 1

        System.out.println("Welcome to the Room Map!");
        System.out.println("-------------------------");
        System.out.println("To navigate, use the following commands:");
        System.out.println("N - Go North");
        System.out.println("E - Go East");
        System.out.println("S - Go South");
        System.out.println("W - Go West");
        System.out.println("Q - Quit the game");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Room currentRoom = rooms.get(currentPlayerPosition);
            System.out.println("Current Room: " + currentRoom.getTitle());
            System.out.println(currentRoom.getDescription());

            System.out.print("Enter a direction (N/E/S/W/Q): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Goodbye!");
                break;
            }

            movePlayer(input);
        }

        scanner.close();
    }

    private static void initializeMap() {
        rooms = new HashMap<>();

        // Create and add rooms to the map
        for (int i = 1; i <= MAP_SIZE; i++) {
            String title = "Room " + i;
            String description = "This is Room " + i + ".";
            Room room = new Room(title, description);
            rooms.put(i, room);
        }

        // Connect adjacent rooms
        for (int i = 1; i <= MAP_SIZE; i++) {
            Room currentRoom = rooms.get(i);

            // Connect North room
            if (i > 1) {
                Room northRoom = rooms.get(i - 1);
                currentRoom.setNorthRoom(northRoom);
            }

            // Connect East room
            if (i < MAP_SIZE) {
                Room eastRoom = rooms.get(i + 1);
                currentRoom.setEastRoom(eastRoom);
            }

            // Connect South room
            if (i < MAP_SIZE) {
                Room southRoom = rooms.get(i + 1);
                currentRoom.setSouthRoom(southRoom);
            }

            // Connect West room
            if (i > 1) {
                Room westRoom = rooms.get(i - 1);
                currentRoom.setWestRoom(westRoom);
            }
        }
    }

    private static void movePlayer(String direction) {
        Room currentRoom = rooms.get(currentPlayerPosition);

        switch (direction) {
            case "N":
                if (currentRoom.getNorthRoom() != null) {
                    currentPlayerPosition--;
                    System.out.println("Moved North.");
                } else {
                    System.out.println("You cannot go North from here.");
                }
                break;
            case "E":
                if (currentRoom.getEastRoom() != null) {
                    currentPlayerPosition++;
                    System.out.println("Moved East.");
                } else {
                    System.out.println("You cannot go East from here.");
                }
                break;
            case "S":
                if (currentRoom.getSouthRoom() != null) {
                    currentPlayerPosition++;
                    System.out.println("Moved South.");
                } else {
                    System.out.println("You cannot go South from here.");
                }
                break;
            case "W":
                if (currentRoom.getWestRoom() != null) {
                    currentPlayerPosition--;
                    System.out.println("Moved West.");
                } else {
                    System.out.println("You cannot go West from here.");
                }
                break;
            default:
                System.out.println("Invalid direction. Please try again.");
        }

        System.out.println();
    }
}

class Room {
    private String title;
    private String description;
    private Room northRoom;
    private Room eastRoom;
    private Room southRoom;
    private Room westRoom;

    public Room(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public void setNorthRoom(Room northRoom) {
        this.northRoom = northRoom;
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public void setEastRoom(Room eastRoom) {
        this.eastRoom = eastRoom;
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public void setSouthRoom(Room southRoom) {
        this.southRoom = southRoom;
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public void setWestRoom(Room westRoom) {
        this.westRoom = westRoom;
    }
}
