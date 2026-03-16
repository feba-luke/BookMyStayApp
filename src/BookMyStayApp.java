import java.util.HashMap;
import java.util.Map;
abstract class Room {

    String type;
    int beds;
    int size;
    double price;

    Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 500, 300);
    }
}

/* ----------- Inventory (State Holder) ----------- */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0);   // example unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/* ----------- Search Service ----------- */
class RoomSearchService {

    public static void searchAvailableRooms(RoomInventory inventory) {

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.type);

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println();
            }
        }
    }
}

/* ----------- Application Entry ----------- */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("     Hotel Booking System        ");
        System.out.println("         Version 4.0             ");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService.searchAvailableRooms(inventory);

        System.out.println("Search completed. System state unchanged.");
    }
}