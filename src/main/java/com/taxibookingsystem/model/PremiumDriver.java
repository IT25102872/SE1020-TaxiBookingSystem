package com.taxibookingsystem.model;

// Inheritance — PremiumDriver extends Driver
public class PremiumDriver extends Driver {

    private String vehicleType;
    private double rating;

    public PremiumDriver(String driverId, String name, String phone,
                         String licenseNumber, String vehicleType, double rating) {
        super(driverId, name, phone, licenseNumber, "AVAILABLE");
        this.vehicleType = vehicleType;
        this.rating = rating;
    }

    // Polymorphism — override getStatus()
    @Override
    public String getStatus() {
        return "AVAILABLE (" + vehicleType + " | ⭐" + rating + ")";
    }

    public String getVehicleType() { return vehicleType; }
    public double getRating()      { return rating; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setRating(double rating)           { this.rating = rating; }
}
