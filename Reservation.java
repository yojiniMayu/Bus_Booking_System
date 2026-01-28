import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Reservation {
private static List<Bus> buses = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static Queue<Customer> waitingList = new LinkedList<>();


    private String customerId;
    private Bus bus;
    private Customer customer;
    private int seatNumber;

    // Constructor accepting all necessary parameters
    public Reservation(String customerName, Bus bus, Customer customer, int seatNumber) {
        this.customerId = customerId;
        this.bus = bus;
        this.customer = customer;
        this.seatNumber = seatNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Bus getBus() {
        return bus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

}
