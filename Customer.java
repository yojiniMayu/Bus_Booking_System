class Customer {
    private String customerId;
    private String name;
    private String mobileNumber;
    private String email;
    private String city;
    private int age;
    private int reservedSeat;

    public Customer(String customerId, String name, String mobileNumber, String email, String city, int age) {
        this.customerId = customerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.city = city;
        this.age = age;
        this.reservedSeat = -1;  // Initially, no seat is reserved
    }


    // Method to convert Customer details to CSV format
    public String toCSV() {
        return customerId + "," + name + "," + mobileNumber + "," + email + "," + city + "," + age;
    }

    // Static method to create Customer object from CSV line
    public static Customer fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        if (values.length == 6) { // Ensure there are enough values
            return new Customer(values[0], values[1], values[2], values[3], values[4], Integer.parseInt(values[5]));
        }
        return null;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public int getReservedSeat() {
        return reservedSeat;
    }

    public void setReservedSeat(int seat) {
        this.reservedSeat = seat;
    }

   
}