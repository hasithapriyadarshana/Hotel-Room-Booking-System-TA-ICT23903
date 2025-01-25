import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== Hotel Room Booking System ===");
            System.out.println("1. Add Room");
            System.out.println("2. Display All Rooms");
            System.out.println("3. Search Room By Type");
            System.out.println("4. Book a Room");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handles user actions based on their menu choice.
            switch (choice) {
                case 1 -> addRoom(scanner);
                case 2 -> displayAllRooms();
                case 3 -> searchRoomByType(scanner);
                case 4 -> addBooking(scanner);
                case 5 -> cancelBooking(scanner);
                case 6 -> {
                    System.out.println("Exit");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void addRoom(Scanner scanner) {
        System.out.println("== Adding A Room ==");
        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String roomType;
        while (true) {
            System.out.print("Enter Room Type (Single, Double, Suite): ");
            roomType = scanner.nextLine().trim();
            if (roomType.equalsIgnoreCase("Single") || roomType.equalsIgnoreCase("Double") || roomType.equalsIgnoreCase("Suite")) {
                break;
            } else {
                System.out.println("Invalid room type. Please enter 'Single', 'Double', or 'Suite'.");
            }
        }

        System.out.print("Enter Price Per Night: ");
        double pricePerNight = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Is this a Deluxe Room? (yes/no): ");
        boolean isDeluxeRoom = scanner.nextLine().equalsIgnoreCase("yes");

        if (isDeluxeRoom) {
            System.out.print("Is WiFi Included? (yes/no): ");
            boolean isWiFiIncluded = scanner.nextLine().equalsIgnoreCase("yes");
            System.out.print("Is Breakfast Included? (yes/no): ");
            boolean isBreakfastIncluded = scanner.nextLine().equalsIgnoreCase("yes");
            rooms.add(new DeluxeRoom(String.valueOf(roomNumber), roomType, pricePerNight, true, isWiFiIncluded, isBreakfastIncluded));
        } else {
            rooms.add(new Room(String.valueOf(roomNumber), roomType, pricePerNight, true));
        }
        System.out.println("Room added successfully!\n");
    }

    public static void searchRoomByType(Scanner scanner) {
        System.out.println("== Searching Room By Type ==");
        System.out.print("Enter Room Type to Search (Single, Double, Suite): ");
        String roomType = scanner.nextLine().trim();
        boolean found = false;
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                room.displayRoomInfo();
                found = true;
                System.out.println();
            }
        }
        if (!found) {
            System.out.println("No rooms of the specified type were found.");
        }
    }

    public static void addBooking(Scanner scanner) {
        System.out.println("== Booking Room ==");
        System.out.print("Enter Room Number to Book: ");
        String roomNumber = scanner.nextLine().trim();
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && room.isAvailable()) {
                room.setAvailable(false);
                System.out.println("Room booked successfully!");
                return;
            }
        }
        System.out.println("Room not available or invalid room number.");
    }

    public static void cancelBooking(Scanner scanner) {
        System.out.println("== Cancel Booking ==");
        System.out.print("Enter Room Number to Cancel Booking: ");
        String roomNumber = scanner.nextLine().trim();
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && !room.isAvailable()) {
                room.setAvailable(true);
                System.out.println("Booking canceled successfully!");
                return;
            }
        }
        System.out.println("Room is not booked or invalid room number.");
    }

    public static void displayAllRooms() {
        System.out.println("== Displaying All Rooms ==");
        for (Room room : rooms) {
            room.displayRoomInfo();
            System.out.println();
        }
    }
}

class Room {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(String roomNumber, String roomType, double pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
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

    public void displayRoomInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType.toUpperCase());
        System.out.println("Price per Night: " + pricePerNight);
        System.out.println("Available: " + isAvailable);
    }
}

class DeluxeRoom extends Room {
    private boolean wifiIncluded;
    private boolean breakfastIncluded;

    public DeluxeRoom(String roomNumber, String roomType, double pricePerNight, boolean isAvailable, boolean wifiIncluded, boolean breakfastIncluded) {
        super(roomNumber, roomType, pricePerNight, isAvailable);
        this.wifiIncluded = wifiIncluded;
        this.breakfastIncluded = breakfastIncluded;
    }

    public boolean isWifiIncluded() {
        return wifiIncluded;
    }

    public boolean isBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Override
    public void displayRoomInfo() {
        super.displayRoomInfo();
        System.out.println("WiFi Included: " + wifiIncluded);
        System.out.println("Breakfast Included: " + breakfastIncluded);
    }
}
