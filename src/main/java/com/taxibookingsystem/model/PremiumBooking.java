package com.taxibookingsystem.model;

// Inheritance — PremiumBooking extends Booking
public class PremiumBooking extends Booking {

    private String vehicleType;
    private boolean airportPickup;

    public PremiumBooking(String bookingId, String passengerName,
                          String pickupLocation, String dropLocation,
                          String vehicleType, boolean airportPickup) {
        super(bookingId, passengerName, pickupLocation, dropLocation);
        this.vehicleType = vehicleType;
        this.airportPickup = airportPickup;
    }

    // Polymorphism — override calculateFare()
    @Override
    public double calculateFare(double distanceKm) {
        double baseFare = distanceKm * 100; // Rs.100 per km
        if (airportPickup) baseFare += 500;  // Airport extra charge
        return baseFare;
    }

    public String getVehicleType()  { return vehicleType; }
    public boolean isAirportPickup() { return airportPickup; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setAirportPickup(boolean airportPickup) { this.airportPickup = airportPickup; }
}
