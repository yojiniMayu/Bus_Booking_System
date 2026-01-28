
class Bus {
   // private List<Seat> seats;  // List of seats, each seat is an object
    private String busNumber;
    private int totalSeats;
    private String startLocation;
    private String endLocation;
    private String startTime;
    private double fare;
    private boolean[] reservedSeats; // Array to track reserved seats
 //   private Reservation[] reservations;  // Array to store reservation details for each seat

    public Bus(String busNumber, int totalSeats, String startLocation, String endLocation, String startTime, double fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.fare = fare;
        this.reservedSeats = new boolean[totalSeats]; // Track reserved seats (false = unreserved)
      //  this.seats = new ArrayList<>();
      //  this.reservations = new Reservation[totalSeats]; 
       /*  // Initialize reservations array
        for (int i = 0; i < totalSeats; i++) {
            seats.add(new Seat(i + 1)); // Add seat numbers starting from 1
        }*/
    }
        public static Bus fromCSV(String csvLine) {
            // Logic to create a Bus object from the CSV line
            // Example:
            String[] values = csvLine.split(",");
            String busNumber = values[0];
            int totalSeats = Integer.parseInt(values[1]);
            String startLocation = values[2];
            String endLocation = values[3];
            String startTime = values[4];
            double fare = Double.parseDouble(values[5]);
            return new Bus(busNumber, totalSeats, startLocation, endLocation, startTime, fare);
            
        }
    
        
        // Reserve the seat
        public int reserveSeat() {
            for (int i = 0; i < reservedSeats.length; i++) {
                if (!reservedSeats[i]) {  // If the seat is not reserved
                    reservedSeats[i] = true;  // Mark the seat as reserved
                    return i + 1;  // Return the seat number (1-indexed)
                }
            }
            return -1;  // No available seats
        }
    
    
        
        public String getBusNumber() {
            return busNumber;
        }
    
        public String getStartLocation() {
            return startLocation;
        }
    
        public String getEndLocation() {
            return endLocation;
        }
    
        public String getStartTime() {
            return startTime;
        }
    
        public double getFare() {
            return fare;
        }
    
    
        public int getTotalSeats() {
            return totalSeats;
        }
    
        public boolean[] getReservedSeats() {
            return reservedSeats;
        }
        // Add this method to convert bus details to a CSV format
        public String toCSV() {
            return busNumber + "," + totalSeats + "," + startLocation + "," + endLocation + "," + startTime + "," + fare;
        }
    
        // Method to cancel reservation
        public boolean cancelSeat(int seatNumber) {
            if (seatNumber < 1 || seatNumber > totalSeats || !reservedSeats[seatNumber - 1]) {
                return false;
            }
            reservedSeats[seatNumber - 1] = false;
            return true;
        }
    

   // Method to get the seat information
   public boolean isSeatAvailable(int seatNumber) {
    return seatNumber >= 1 && seatNumber <= totalSeats && !reservedSeats[seatNumber - 1];
}

// Get the first available seat (starting from seat number 1)
public int getFirstAvailableSeat() {
    for (int i = 0; i < reservedSeats.length; i++) {
        if (!reservedSeats[i]) {
            return i + 1; // Return the first available seat number (1-indexed)
        }
    }
    return -1; // No available seat
}

// Check if the bus is full
public boolean isFull() {
    for (boolean seat : reservedSeats) {
        if (!seat) {
            return false;  // There is at least one available seat
        }
    }
    return true;  // All seats are taken
}

// Method to get bus details as a string
    public String getBusDetails() {
        return "Bus Number: " + busNumber + ", Total Seats: " + totalSeats + ", Start Location: " + startLocation
                + ", End Location: " + endLocation + ", Start Time: " + startTime + ", Fare: " + fare;
    }
    public void reserveSeat(int seatNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reserveSeat'");
    }


}

