import java.util.ArrayList;
import java.util.Scanner;

public class HotelBookingSystem {
    // List to store all the rooms in the hotel
    private static ArrayList<Room> roomList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu options
            System.out.println("=== Hotel Room Booking System ===");
            System.out.println("1. Add Room");
            System.out.println("2. Display All Rooms");
            System.out.println("3. Search Room By Type");
            System.out.println("4. Book a Room");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");

            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Execute actions based on user choice
            switch (userChoice) {
                case 1 -> addRoom(scanner);
                case 2 -> displayAllRooms();
                case 3 -> searchRoomByType(scanner);
                case 4 -> bookRoom(scanner);
                case 5 -> cancelRoomBooking(scanner);
                case 6 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a new room to the system
    public static void addRoom(Scanner scanner) {
        System.out.println("== Adding A New Room ==");

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
        boolean isDeluxe = scanner.nextLine().equalsIgnoreCase("yes");

        if (isDeluxe) {
            System.out.print("Is WiFi Included? (yes/no): ");
            boolean hasWiFi = scanner.nextLine().equalsIgnoreCase("yes");

            System.out.print("Is Breakfast Included? (yes/no): ");
            boolean hasBreakfast = scanner.nextLine().equalsIgnoreCase("yes");

            roomList.add(new DeluxeRoom(String.valueOf(roomNumber), roomType, pricePerNight, true, hasWiFi, hasBreakfast));
        } else {
            roomList.add(new Room(String.valueOf(roomNumber), roomType, pricePerNight, true));
        }

        System.out.println("Room added successfully!\n");
    }

    // Method to display all rooms
    public static void displayAllRooms() {
        System.out.println("== Displaying All Rooms ==");
        for (Room room : roomList) {
            room.displayRoomInfo();
            System.out.println();
        }
    }

    // Method to search for rooms by type
    public static void searchRoomByType(Scanner scanner) {
        System.out.println("== Search Rooms By Type ==");
        System.out.print("Enter Room Type (Single, Double, Suite): ");
        String roomType = scanner.nextLine().trim();

        boolean roomFound = false;
        for (Room room : roomList) {
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                room.displayRoomInfo();
                System.out.println();
                roomFound = true;
            }
        }

        if (!roomFound) {
            System.out.println("No rooms of the specified type found.");
        }
    }

    // Method to book a room
    public static void bookRoom(Scanner scanner) {
        System.out.println("== Book A Room ==");
        System.out.print("Enter Room Number to Book: ");
        String roomNumber = scanner.nextLine().trim();

        for (Room room : roomList) {
            if (room.getRoomNumber().equals(roomNumber) && room.isAvailable()) {
                room.setAvailable(false);
                System.out.println("Room booked successfully!");
                return;
            }
        }

        System.out.println("Room not available or invalid room number.");
    }

    // Method to cancel a room booking
    public static void cancelRoomBooking(Scanner scanner) {
        System.out.println("== Cancel Room Booking ==");
        System.out.print("Enter Room Number to Cancel Booking: ");
        String roomNumber = scanner.nextLine().trim();

        for (Room room : roomList) {
            if (room.getRoomNumber().equals(roomNumber) && !room.isAvailable()) {
                room.setAvailable(true);
                System.out.println("Booking canceled successfully!");
                return;
            }
        }

        System.out.println("Room is not booked or invalid room number.");
    }
}

// Basic Room class to define room attributes
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void displayRoomInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price Per Night: " + pricePerNight);
        System.out.println("Available: " + isAvailable);
    }
}

// DeluxeRoom class extending Room with additional features
class DeluxeRoom extends Room {
    private boolean wifiIncluded;
    private boolean breakfastIncluded;

    public DeluxeRoom(String roomNumber, String roomType, double pricePerNight, boolean isAvailable, boolean wifiIncluded, boolean breakfastIncluded) {
        super(roomNumber, roomType, pricePerNight, isAvailable);
        this.wifiIncluded = wifiIncluded;
        this.breakfastIncluded = breakfastIncluded;
    }

    @Override
    public void displayRoomInfo() {
        super.displayRoomInfo();
        System.out.println("WiFi Included: " + wifiIncluded);
        System.out.println("Breakfast Included: " + breakfastIncluded);
    }
}