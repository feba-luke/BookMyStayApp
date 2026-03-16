import java.util.*;
/* -------- Reservation Model -------- */
class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/* -------- Inventory Service -------- */
class InventoryService {

    private HashMap<String, Integer> inventory;

    InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }
}

/* -------- Booking Service -------- */
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventory;

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.requestQueue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation request = requestQueue.poll();

            String roomType = request.roomType;

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.replace(" ", "").substring(0,2).toUpperCase() + roomCounter++;

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println();
            }
            else {

                System.out.println("Reservation Failed for " + request.guestName +
                        " (No " + roomType + " available)\n");
            }
        }
    }

    public void displayAllocations() {

        System.out.println("---- Room Allocations ----");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/* -------- Application Entry -------- */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("     Hotel Booking System        ");
        System.out.println("         Version 6.0             ");
        System.out.println("=================================");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));
        bookingQueue.add(new Reservation("Eva", "Suite Room"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(bookingQueue, inventory);

        bookingService.processBookings();

        bookingService.displayAllocations();
    }
}