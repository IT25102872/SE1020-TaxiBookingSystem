package com.taxibookingsystem.model;

public class Booking {
    // private fields — block the direct access
    private String bookingId;
    private String passengerName;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private double fare;

    // Constructor
    public Booking(String bookingId, String passengerName,
                   String pickupLocation, String dropLocation) {
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.status = "PENDING";
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getPassengerName() { return passengerName; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropLocation() { return dropLocation; }
    public String getStatus() { return status; }
    public double getFare() { return fare; }

    // Setters
    public void setStatus(String status) { this.status = status; }
    public void setFare(double fare) { this.fare = fare; }

    // Fare calculation method — for Polymorphism
    public double calculateFare(double distanceKm) {
        return distanceKm * 50; // Rs.50 per km
    }

    // File write — CSV format
    public String toFileString() {
        return bookingId + "," + passengerName + ","
                + pickupLocation + "," + dropLocation + "," + status + "," + fare;
    }
}
