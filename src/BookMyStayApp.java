import java.util.LinkedList;
import java.util.Queue;
/* -------- Reservation Model -------- */
class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/* -------- Booking Request Queue -------- */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    public void displayRequests() {
        System.out.println("\n--- Booking Request Queue ---");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}

/* -------- Application Entry -------- */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("     Hotel Booking System        ");
        System.out.println("         Version 5.0             ");
        System.out.println("=================================");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayRequests();

        System.out.println("\nRequests stored in FIFO order. Allocation will happen later.");
    }
}