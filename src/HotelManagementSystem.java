import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Guest {
    private String name;
    private int id;
    private Room room;

    public Guest(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

class Reservation {
    private static int counter = 1;
    private int id;
    private Guest guest;
    private Room room;

    public Reservation(Guest guest, Room room) {
        this.id = counter++;
        this.guest = guest;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }
}

class Hotel {
    private String name;
    private List<Room> rooms;
    private Map<Integer, Guest> guestMap;
    private Map<Integer, Reservation> reservationMap;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.guestMap = new HashMap<>();
        this.reservationMap = new HashMap<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }


    public void checkIn(int roomNumber, Guest guest) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            room.setGuest(guest);
            room.book();
            guestMap.put(guest.getId(), guest);
            System.out.println("Guest " + guest.getName() + " has checked in to room " + room.getNumber());
        } else {
            System.out.println("Invalid room number or room already booked.");
        }
    }

    public void checkOut(int roomNumber) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null && room.isBooked()) {
            Guest guest = room.getGuest();
            room.unbook();
            guest.setRoom(null);
            System.out.println("Guest " + guest.getName() + " has checked out from Room " + room.getNumber());
        } else {
            System.out.println("Invalid room number or room is not booked.");
        }
    }


    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room.getNumber());
            }
        }
    }

    public void makeReservation(int roomNumber, Guest guest) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            Reservation reservation = new Reservation(guest, room);
            reservationMap.put(reservation.getId(), reservation);
            System.out.println("Reservation made for " + guest.getName() + " in room " + room.getNumber());
        } else {
            System.out.println("Invalid room number or room already booked.");
        }
    }

    public void cancelReservation(int reservationId) {
        Reservation reservation = reservationMap.get(reservationId);
        if (reservation != null) {
            reservationMap.remove(reservationId);
            System.out.println("Reservation cancelled for " + reservation.getGuest().getName());
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }
    public Guest getGuestById(int guestId) {
        for (Room room : rooms) {
            Guest guest = room.getGuest();
            if (guest != null && guest.getId() == guestId) {
                return guest;
            }
        }
        return null; // Guest not found
    }

    public void displayGuestInformation(int guestId) {
        Guest guest = getGuestById(guestId);
        if (guest != null) {
            System.out.println("Guest Information:");
            System.out.println("Guest ID: " + guest.getId());
            System.out.println("Guest Name: " + guest.getName());
            Room room = guest.getRoom();
            if (room != null) {
                System.out.println("Room Number: " + room.getNumber());
            } else {
                System.out.println("Room Number: Not assigned");
            }
        } else {
            System.out.println("Guest not found.");
        }
    }


    public String getName() {
        return name;
    }
}

class Room {
    private int number;
    private boolean booked;
    private Guest guest;

    public Room(int number) {
        this.number = number;
        this.booked = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBooked() {
        return booked;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void book() {
        booked = true;
    }

    public void unbook() {
        booked = false;
    }
}

public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("Example Hotel");

        // Create rooms
        hotel.addRoom(new Room(101));
        hotel.addRoom(new Room(102));
        hotel.addRoom(new Room(103));
        hotel.addRoom(new Room(201));
        hotel.addRoom(new Room(202));

        while (true) {
            System.out.println("\nWelcome to " + hotel.getName() + "!");
            System.out.println("1. Book a room");
            System.out.println("2. Check available rooms");
            System.out.println("3. Check-in");
            System.out.println("4. Check-out");
            System.out.println("5. Make a reservation");
            System.out.println("6. Cancel a reservation");
            System.out.println("7. Display guest information");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the room number you want to book: ");
                    int roomNumber = scanner.nextInt();
                    Room room = hotel.getRoomByNumber(roomNumber);
                    if (room != null && !room.isBooked()) {
                        Guest guest = new Guest("", -1);
                        room.setGuest(guest);
                        room.book();
                        System.out.println("Room " + room.getNumber() + " has been booked.");
                    } else {
                        System.out.println("Invalid room number or room already booked.");
                    }
                    break;
                case 2:
                    hotel.displayAvailableRooms();
                    break;
                case 3:
                    System.out.print("Enter the room number: ");
                    roomNumber = scanner.nextInt();
                    System.out.print("Enter the guest name: ");
                    scanner.nextLine(); // Consume the newline character
                    String guestName = scanner.nextLine();
                    System.out.print("Enter the guest ID: ");
                    int guestId = scanner.nextInt();
                    Guest guest = new Guest(guestName, guestId);
                    hotel.checkIn(roomNumber, guest);
                    break;
                case 4:
                    System.out.print("Enter the room number: ");
                    roomNumber = scanner.nextInt();
                    hotel.checkOut(roomNumber);
                    break;
                case 5:
                    System.out.print("Enter the room number: ");
                    roomNumber = scanner.nextInt();
                    System.out.print("Enter the guest name: ");
                    scanner.nextLine(); // Consume the newline character
                    guestName = scanner.nextLine();
                    System.out.print("Enter the guest ID: ");
                    guestId = scanner.nextInt();
                    guest = new Guest(guestName, guestId);
                    hotel.makeReservation(roomNumber, guest);
                    break;
                case 6:
                    System.out.print("Enter the reservation ID: ");
                    int reservationId = scanner.nextInt();
                    hotel.cancelReservation(reservationId);
                    break;
                case 7:
                    System.out.print("Enter the guest ID: ");
                    guestId = scanner.nextInt();
                    hotel.displayGuestInformation(guestId);
                    break;
                case 8:
                    System.out.println("Thank you for using the hotel management system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            scanner.close();
        }
    }
}
