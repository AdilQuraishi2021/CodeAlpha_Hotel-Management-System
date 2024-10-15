import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;

class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Category: " + category + ", Price per night: $" + pricePerNight;
    }
}

class Hotel {
    private List<Room> rooms;

    public Hotel() {
        rooms = new ArrayList<>();
        rooms.add(new Room(101, "Single", 1000));
        rooms.add(new Room(102, "Single", 800));
        rooms.add(new Room(201, "Double", 1500));
        rooms.add(new Room(202, "Double", 1200));
        rooms.add(new Room(301, "Suite", 2500));
        rooms.add(new Room(302, "Suite", 2100));
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}

class Reservation {
    private Room room;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(Room room, String guestName, String checkInDate, String checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        room.setAvailable(false);
    }

    @Override
    public String toString() {
        return "Reservation Details:\n" +
                "Guest Name: " + guestName + "\n" +
                "Room: " + room + "\n" +
                "Check-in Date: " + checkInDate + "\n" +
                "Check-out Date: " + checkOutDate;
    }
}

class Payment {
    private double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public void processPayment(String paymentMethod) {
        // Simulating payment processing
        System.out.println("Processing " + paymentMethod + " payment of Rs" + amount);
        System.out.println("Payment successful!");
    }
}

public class HotelReservationSystem {
    private Hotel hotel;
    private List<Reservation> reservations;

    public HotelReservationSystem() {
        hotel = new Hotel();
        reservations = new ArrayList<>();
    }

    public void searchAndReserveRoom() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter room category (Single/Double/Suite): ");
            String category = scanner.nextLine();
            List<Room> availableRooms = hotel.searchAvailableRooms(category);

            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available in this category.");
                return;
            }

            System.out.println("Available rooms:");
            for (Room room : availableRooms) {
                System.out.println(room);
            }

            System.out.println("Enter room number to reserve: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Room room = hotel.getRoomByNumber(roomNumber);
            if (room != null && room.isAvailable()) {
                System.out.println("Enter your name: ");
                String guestName = scanner.nextLine();

                System.out.println("Enter check-in date (YYYY-MM-DD): ");
                String checkInDate = scanner.nextLine();

                System.out.println("Enter check-out date (YYYY-MM-DD): ");
                String checkOutDate = scanner.nextLine();

                Reservation reservation = new Reservation(room, guestName, checkInDate, checkOutDate);
                reservations.add(reservation);

                System.out.println("Reservation successful!");
                System.out.println(reservation);

                // Payment processing
                double totalAmount = room.getPricePerNight() * calculateNights(checkInDate, checkOutDate);
                Payment payment = new Payment(totalAmount);
                System.out.println("Total amount due: $" + totalAmount);

                System.out.println("Enter payment method (Credit/Debit/Cash): ");
                String paymentMethod = scanner.nextLine();
                payment.processPayment(paymentMethod);
            } else {
                System.out.println("Room is not available.");
            }
        }
    }

    public static long calculateNights(String checkInDate, String checkOutDate) {
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nHotel Reservation System");
                System.out.println("1. Search and Reserve Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Exit");
                // scanner.nextLine();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        system.searchAndReserveRoom();
                        break;
                    case 2:
                        system.viewReservations();
                        break;
                    case 3:
                        System.out.println("Exiting.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please, enter correct choice.");
                }
            }
        }
    }
}
