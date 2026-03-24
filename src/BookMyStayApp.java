import java.util.*;

// ================== CUSTOM EXCEPTION ==================
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ================== RESERVATION ==================
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int roomsBooked;
    private double baseAmount;

    public Reservation(String reservationId, String guestName, String roomType,
                       int roomsBooked, double baseAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomsBooked = roomsBooked;
        this.baseAmount = baseAmount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Rooms: " + roomsBooked +
                ", Base Amount: ₹" + baseAmount;
    }
}

// ================== ADD-ON SERVICE ==================
class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (₹" + price + ")";
    }
}

// ================== ADD-ON MANAGER ==================
class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateCost(String reservationId) {
        double total = 0;
        for (AddOnService s : getServices(reservationId)) {
            total += s.getPrice();
        }
        return total;
    }
}

// ================== BOOKING HISTORY ==================
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

// ================== VALIDATOR ==================
class Validator {
    private static final Set<String> VALID_TYPES =
            new HashSet<>(Arrays.asList("Standard", "Deluxe", "Suite"));

    public static void validate(String type, int count, Map<String, Integer> inventory)
            throws InvalidBookingException {

        if (!VALID_TYPES.contains(type)) {
            throw new InvalidBookingException("Invalid room type!");
        }

        if (count <= 0) {
            throw new InvalidBookingException("Room count must be > 0");
        }

        int available = inventory.getOrDefault(type, 0);

        if (count > available) {
            throw new InvalidBookingException("Not enough rooms available!");
        }
    }
}

// ================== BOOKING SERVICE ==================
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();
    private BookingHistory history;

    public BookingService(BookingHistory history) {
        this.history = history;

        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public Reservation book(String id, String name, String type, int count)
            throws InvalidBookingException {

        Validator.validate(type, count, inventory);

        inventory.put(type, inventory.get(type) - count);

        double basePrice = switch (type) {
            case "Standard" -> 2000;
            case "Deluxe" -> 3000;
            case "Suite" -> 5000;
            default -> 0;
        };

        Reservation r = new Reservation(id, name, type, count, basePrice * count);

        history.add(r);
        return r;
    }

    public void showInventory() {
        System.out.println("\nInventory:");
        for (var e : inventory.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}

// ================== REPORT SERVICE ==================
class ReportService {
    public void showHistory(List<Reservation> list) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : list) {
            System.out.println(r);
        }
    }

    public void showSummary(List<Reservation> list, AddOnServiceManager addOnManager) {
        double total = 0;

        for (Reservation r : list) {
            total += r.getBaseAmount();
            total += addOnManager.calculateCost(r.getReservationId());
        }

        System.out.println("\nTotal Bookings: " + list.size());
        System.out.println("Total Revenue (with add-ons): ₹" + total);
    }
}

// ================== MAIN ==================
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        AddOnServiceManager addOnManager = new AddOnServiceManager();
        BookingService bookingService = new BookingService(history);
        ReportService reportService = new ReportService();

        try {
            bookingService.showInventory();

            System.out.print("\nEnter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            System.out.print("Enter Room Count: ");
            int count = sc.nextInt();

            Reservation r = bookingService.book(id, name, type, count);

            System.out.println("Booking Successful!");

            // Add-on selection
            addOnManager.addService(id, new AddOnService("WiFi", 200));
            addOnManager.addService(id, new AddOnService("Breakfast", 500));

            // Show bill
            double total = r.getBaseAmount() + addOnManager.calculateCost(id);

            System.out.println("\nFinal Bill:");
            System.out.println(r);
            System.out.println("Add-On Cost: ₹" + addOnManager.calculateCost(id));
            System.out.println("Total Amount: ₹" + total);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Reporting
        reportService.showHistory(history.getAll());
        reportService.showSummary(history.getAll(), addOnManager);

        sc.close();
    }
}