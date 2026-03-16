import java.util.HashMap;
import java.util.Map;
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("     Hotel Booking System        ");
        System.out.println("         Version 3.1             ");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability of Double Room...");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nUpdating availability of Double Room...");
        inventory.updateAvailability("Double Room", 5);

        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}