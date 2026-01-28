
import java.io.*;
import java.util.*;

public class BusReservationSystem {
    private static List<Customer> customers = new ArrayList<>();
    private static List<Bus> buses = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static Queue<Customer> waitingQueue = new LinkedList<>();
    private static int customerCount = 1;
    
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("......Bus Reservation System......");
            System.out.println("1. Register Customer");
            System.out.println("2. Register Bus");
            System.out.println("3. Search Bus");
            System.out.println("4. Reserve Seat");
            System.out.println("5. Additional Reserve Seat");
            System.out.println("6. Cancel Reservation");
            System.out.println("7. Display All Reservations");
            System.out.println("8. Waiting List Details");
            System.out.println("9. View Registered Customers (Newest to Oldest)");
            System.out.println("10.View Registered Bus Detail");
            System.out.println("0. Exit");

            System.out.print("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            loadCustomersFromTextFile();
            loadBusesFromTextFile();
            

         switch (choice) {
            case 1 -> registerCustomer(scanner);
            case 2 -> registerBus(scanner);
            case 3 -> searchBus(scanner);
            case 4 -> reserveSeat(scanner);
            case 5 -> additionalReserveSeat(scanner);
            case 6 -> cancelReservation(scanner);
            case 7 -> displayReservations(scanner);
            case 8 -> displayWaitingList();
            case 9 -> viewRegisteredCustomers();
            case 10 -> viewRegisteredBuses();
            case 0 -> System.out.println("Thank you for using the Bus Reservation System!");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 0);
    // Save data to text files before exiting
   // saveCustomersToTextFile();
    saveBusesToTextFile();
    
}


// Method for registering customer details
private static void registerCustomer(Scanner scanner) {
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Mobile Number: ");
    String mobileNumber = scanner.nextLine();

    // Check if the mobile number is already registered
    for (Customer customer : customers) {
        if (customer.getMobileNumber().equals(mobileNumber)) {
            System.out.println("This mobile number is already registered.");
            return; // Exit the method if the number is already registered
        }
    }

    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter City: ");
    String city = scanner.nextLine();
    System.out.print("Enter Age: ");
    int age = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character

    // Create a new customer ID and customer object
    String customerId = "XYZ-C " + customerCount;
    customerCount++; // Increment customerCount after using it

    // Create the new Customer object and add it to the list
    Customer newCustomer = new Customer(customerId, name, mobileNumber, email, city, age);
    customers.add(newCustomer);

    // Save the new customer to the text file
    saveCustomerToTextFile(newCustomer);
    System.out.println("Customer Registered Successfully!");    //displayAllCustomers();
}


    // Method to save customer details to a text file
   private static void saveCustomerToTextFile(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            writer.write(customer.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving customer details: " + e.getMessage());
        }
    }

// Load customers from file at the start
public static void loadCustomersFromTextFile() {
    customers.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            Customer customer = Customer.fromCSV(line);
            if (customer != null) {
                customers.add(customer);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading customer details: " + e.getMessage());
    }
    for (Customer customer : customers) {
        String customerId = customer.getCustomerId();
        int idNumber = Integer.parseInt(customerId.split(" ")[1]);
        if (idNumber >= customerCount) {
            customerCount = idNumber + 1;
        }
    }
}


private static void registerBus(Scanner scanner) {
    System.out.print("Enter Bus Number: ");
    String busNumber = scanner.nextLine();

    // Check for existing bus number
    for (Bus bus : buses) {
        if (bus.getBusNumber().equalsIgnoreCase(busNumber)) {
            System.err.println("This number is already registered, can't register.");
            return; // Exit if already exists
        }
    }

    System.out.print("Enter Total Seats: ");
    int totalSeats = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter Starting Location: ");
    String startLocation = scanner.nextLine();
    System.out.print("Enter Ending Location: ");
    String endLocation = scanner.nextLine();
    System.out.print("Enter Starting Time: ");
    String startTime = scanner.nextLine();
    System.out.print("Enter Fare: ");
    double fare = scanner.nextDouble();

    buses.add(new Bus(busNumber, totalSeats, startLocation, endLocation, startTime, fare));
    System.out.println("Bus Registered Successfully!");
}





    private static void searchBus(Scanner scanner) {
        System.out.print("Enter Starting Location: ");
        String startLocation = scanner.nextLine();
        System.out.print("Enter Ending Location: ");
        String endLocation = scanner.nextLine();
        System.out.println("Available Buses from " + startLocation + " to " + endLocation + ":");
    
        boolean found = false;
        for (Bus bus : buses) {
            if (bus.getStartLocation().equalsIgnoreCase(startLocation) &&
                bus.getEndLocation().equalsIgnoreCase(endLocation)) {
    
                int reservedSeats = calculateReservedSeats(bus);
                int availableSeats = bus.getTotalSeats() - reservedSeats;
    
                found = true;
                System.out.println("Bus Number: " + bus.getBusNumber() +
                                   ", Start Time: " + bus.getStartTime() +
                                   ", Fare: " + bus.getFare() +
                                   ", Available Seats: " + availableSeats);
            }
        }
        if (!found) {
            System.out.println("No buses available for this route.");
        }
    }
    
    // Helper method to calculate reserved seats for a specific bus
    private static int calculateReservedSeats(Bus bus) {
        int reservedSeats = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getBus().getBusNumber().equals(bus.getBusNumber())) {
                reservedSeats++;
            }
        }
        return reservedSeats;
    }


    // Method to save bus details to a text file
    private static void saveBusToTextFile(Bus bus) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("buses.txt", true))) {
            writer.write(bus.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving bus details: " + e.getMessage());
        }
    }
/* 
// Convert Bus object to CSV format
private static String busToCSV(Bus bus) {
    return bus.getBusNumber() + "," + bus.getTotalSeats() + "," + bus.getStartLocation() + ","
           + bus.getEndLocation() + "," + bus.getStartTime() + "," + bus.getFare();
}

// Method to load buses from a text file
public static Bus fromCSV(String csvLine) {
    String[] parts = csvLine.split(",");
    if (parts.length == 6) {
        String busNumber = parts[0].trim();
        int totalSeats = Integer.parseInt(parts[1].trim());
        String startLocation = parts[2].trim();
        String endLocation = parts[3].trim();
        String startTime = parts[4].trim();
        double fare = Double.parseDouble(parts[5].trim());
        return new Bus(busNumber, totalSeats, startLocation, endLocation, startTime, fare);
    }
    return null;
}
    */

private static void reserveSeat(Scanner scanner) {
    // Prompt for bus number and customer mobile number
    System.out.print("Enter Bus Number: ");
    String busNumber = scanner.nextLine();
    System.out.print("Enter Customer Mobile Number: ");
    String mobileNumber = scanner.nextLine();

    // Find the bus using busNumber
    Bus bus = findBusByNumber(busNumber);
    
    // Find the customer using mobileNumber
    Customer customer = findCustomerByMobile(mobileNumber);

    // Check if the bus was found
    if (bus == null) {
        System.out.println("Bus not found.");
        return;
    }

    // Check if the customer was found
    if (customer == null) {
        System.out.println("Customer not found. Please register customer via option 1.");
        return;
    }

    int seatNumber = bus.getFirstAvailableSeat();
    if (seatNumber != -1) {
        bus.reserveSeat(seatNumber);
        reservations.add(new Reservation(customer.getCustomerId(), bus, customer, seatNumber));
        System.out.println("Reservation successfully made for " + customer.getName() + " on seat " + seatNumber);
    } else {
        waitingQueue.add(customer);
        System.out.println("No available seats. Added to waiting list.");
    }

}




private static void saveReservationToFile(Reservation reservation) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservation.txt", true))) {
        writer.write("Customer: " + reservation.getCustomer().getName() + ", ");
        writer.write("Bus Number: " + reservation.getBus().getBusNumber() + ", ");
        writer.write("Seat Number: " + reservation.getSeatNumber() + "\n");
        writer.flush();
    } catch (IOException e) {
        System.out.println("An error occurred while saving the reservation.");
        e.printStackTrace();
    }
}


private static void loadBusesFromTextFile() {
    buses.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader("buses.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            Bus bus = Bus.fromCSV(line); // Ensure this method is correctly implemented and accessible
            buses.add(bus);
        }
    } catch (IOException e) {
        System.out.println("Error loading bus details: " + e.getMessage());
    }
}

private static void saveBusesToTextFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("buses.txt"))) {
        for (Bus bus : buses) {
            writer.write(bus.toCSV()); // Ensure this method is correctly implemented and accessible
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving bus details: " + e.getMessage());
    }
}


private static void additionalReserveSeat(Scanner scanner) {
    System.out.print("Enter Customer Mobile Number to check additional reservation: ");
    String mobileNumber = scanner.nextLine();
    Customer customer = findCustomerByMobile(mobileNumber);

    if (customer == null) {
        System.out.println("Customer not found. please registor via option 1");
        return;
    }

    boolean hasReservation = false;
    for (Reservation reservation : reservations) {
        if (reservation.getCustomerId().equals(customer.getCustomerId())) {
            hasReservation = true;
            break;
        }
    }

    if (hasReservation) {
        waitingQueue.add(customer);
        System.out.println("Customer has an existing reservation.your request send to waiting list");
    } else {
        System.out.println("Customer does not have a reservation. Please reserve a seat first.");
    }
}




private static void cancelReservation(Scanner scanner) {
    System.out.print("Enter Bus Number: ");
    String busNumber = scanner.nextLine();
    System.out.print("Enter Customer Mobile Number: ");
    String mobileNumber = scanner.nextLine();

    Bus bus = findBusByNumber(busNumber);
    Customer customer = findCustomerByMobile(mobileNumber);

    if (bus == null || customer == null) {
        System.out.println("Bus or Customer not found.");
        return;
    }

    Reservation reservationToCancel = null;
    for (Reservation reservation : reservations) {
        if (reservation.getBus().getBusNumber().equals(busNumber) &&
            reservation.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            reservationToCancel = reservation;
            break;
        }
    }

    if (reservationToCancel != null) {
        // Cancel the reservation
        bus.cancelSeat(reservationToCancel.getSeatNumber());
        System.out.println("Reservation for " + customer.getName() + " on seat " + reservationToCancel.getSeatNumber() + " successfully cancelled.");
        
        // Remove the reservation from the reservations list
        reservations.remove(reservationToCancel);

        // Notify the partner
        int partnerSeat = findPartnerSeat(reservationToCancel.getSeatNumber(), bus);
        if (partnerSeat != -1) {
            Customer partner = findPartnerBySeat(partnerSeat, bus);
            if (partner != null) {
                System.out.println(partner.getName() + ", your partner canceled seat " + reservationToCancel.getSeatNumber());
            }
        }

        // Handle waiting list
        if (!waitingQueue.isEmpty()) {
            Customer waitingCustomer = waitingQueue.poll();
            bus.reserveSeat(reservationToCancel.getSeatNumber());
            System.out.println(waitingCustomer.getName() + ", your seat is reserved on bus " + bus.getBusNumber() + ", Seat No: " + reservationToCancel.getSeatNumber());
            reservations.add(new Reservation(waitingCustomer.getCustomerId(), bus, waitingCustomer, reservationToCancel.getSeatNumber()));
        }
    } else {
        System.out.println("No reservation found for the specified customer on the specified bus.");
    }
}


private static void displayReservations(Scanner scanner) {
    System.out.print("Enter Bus Number to view reservations: ");
    String busNumber = scanner.nextLine();
    
    Bus bus = findBusByNumber(busNumber);
    
    if (bus == null) {
        System.out.println("Bus not found.");
        return;
    }

    System.out.println("Reservations for Bus Number: " + busNumber);
    boolean found = false;
    for (Reservation reservation : reservations) {
        if (reservation.getBus().getBusNumber().equals(busNumber)) {
            System.out.println("Customer: " + reservation.getCustomer().getName() +
                               ", Seat Number: " + reservation.getSeatNumber());
            found = true;
        }
    }
    
    if (!found) {
        System.out.println("No reservations found for this bus.");
    }
}




private static void displayWaitingList() {
    System.out.println("Waiting List:");
    if (waitingQueue.isEmpty()) {
        System.out.println("No customers on the waiting list.");
        return;
    }

    int position = 1;
    for (Customer customer : waitingQueue) {
        System.out.println("Position " + position + ": " + customer.getName());
        position++;
    }
}


private static void viewRegisteredCustomers() {
    System.out.println("Registered Customers (Newest to Oldest):");
    for (int i = customers.size() - 1; i >= 0; i--) {
        Customer customer = customers.get(i);
        System.out.println("Name: " + customer.getName() + ", Mobile: " + customer.getMobileNumber());
    }
}

private static void viewRegisteredBuses() {
    System.out.println("Registered Buses (Newest to Oldest):");
    for (int i = buses.size() - 1; i >= 0; i--) {
        Bus bus = buses.get(i);
        System.out.println("Bus Number: " + bus.getBusNumber() + ", Total Seats: " + bus.getTotalSeats() + 
                           ", Starting Point: " + bus.getStartLocation() + ", Ending Point: " + bus.getEndLocation() +
                           ", Starting Time: " + bus.getStartTime() + ", Fare: " + bus.getFare());
    }
}

private static int findPartnerSeat(int seatNumber, Bus bus) {
    // Assume seat pairs: 1-2, 2-3, etc.
    if (seatNumber == 1) return 2; // Seat 1's partner is Seat 2
    if (seatNumber == bus.getTotalSeats()) return bus.getTotalSeats() - 1; // Last seat has the previous one as partner
    return seatNumber % 2 == 0 ? seatNumber - 1 : seatNumber + 1; // Even seat has the previous one as partner, odd has the next
}

private static Customer findPartnerBySeat(int seatNumber, Bus bus) {
    for (Reservation reservation : reservations) {
        if (reservation.getBus().equals(bus) && reservation.getSeatNumber() == seatNumber) {
            return reservation.getCustomer();
        }
    }
    return null;
}

// Updated method definition to accept the list of buses
private static Bus findBusByNumber(String busNumber) {
    for (Bus bus : buses) {
        if (bus.getBusNumber().equals(busNumber)) {
            return bus; // Return the matching bus
        }
    }
    return null; // Return null if no matching bus is found
}

// Updated method definition to accept the list of customers
private static Customer findCustomerByMobile(String mobileNumber) {
    for (Customer customer : customers) {
        if (customer.getMobileNumber().equals(mobileNumber)) {
            return customer; // Return the matching customer
        }
    }
    return null; // Return null if no matching customer is found
}
private static Customer findCustomer(String customerId) {
    for (Customer customer : customers) {
        if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
            return customer;
        }
    }
    return null;
}

private static Bus findBus(String busNumber) {
    for (Bus bus : buses) {
        if (bus.getBusNumber().equalsIgnoreCase(busNumber)) {
            return bus;
        }
    }
    return null;
}





}
