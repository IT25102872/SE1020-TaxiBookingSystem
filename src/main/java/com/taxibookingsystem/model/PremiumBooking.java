package com.taxibookingsystem.model;

public class PremiumBooking extends Booking{
    private String vehicleType; // "LUXURY" or "SUV"
    private boolean airportPickup;

    public PremiumBooking(String bookingId, String passengerName,
                          String pickupLocation, String dropLocation,
                          String vehicleType, boolean airportPickup) {

        // Call the parent class constructor
        super(bookingId, passengerName, pickupLocation, dropLocation);
        this.vehicleType = vehicleType;
        this.airportPickup = airportPickup;
    }

    // @Override — Polymorphism! Changing the parent method
    @Override
    public double calculateFare(double distanceKm) {
        double baseFare = distanceKm * 100; // Rs.100 per km
        if (airportPickup) baseFare += 500; // Airport extra charge
        return baseFare;
    }

    public String getVehicleType() { return vehicleType; }
    public boolean isAirportPickup() { return airportPickup; }
}
