abstract class Room {

            String roomType;
            int beds;
            int size;
            double price;

            Room(String roomType, int beds, int size, double price) {
                this.roomType = roomType;
                this.beds = beds;
                this.size = size;
                this.price = price;
            }

            void displayDetails() {
                System.out.println("Room Type : " + roomType);
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

        public class BookMyStayApp {

            public static void main(String[] args) {

                System.out.println("=================================");
                System.out.println("     Book My Stay Application    ");
                System.out.println("     Hotel Booking System        ");
                System.out.println("         Version 2.1             ");
                System.out.println("=================================");

                Room single = new SingleRoom();
                Room doubleRoom = new DoubleRoom();
                Room suite = new SuiteRoom();

                int singleAvailable = 10;
                int doubleAvailable = 6;
                int suiteAvailable = 3;

                System.out.println("\n--- Room Details ---");

                single.displayDetails();
                System.out.println("Available : " + singleAvailable + "\n");

                doubleRoom.displayDetails();
                System.out.println("Available : " + doubleAvailable + "\n");

                suite.displayDetails();
                System.out.println("Available : " + suiteAvailable + "\n");

                System.out.println("Application finished.");
            }
        }