package com.taxibookingsystem.model;

// Inheritance — LuxuryVehicle extends Vehicle
public class LuxuryVehicle extends Vehicle {

    private String amenities; // WIFI, AC, TV

    public LuxuryVehicle(String vehicleId, String plateNumber, String brand,
                         String model, String amenities) {
        super(vehicleId, plateNumber, brand, model, "SUV", "AVAILABLE");
        this.amenities = amenities;
    }

    // Polymorphism — override getType()
    @Override
    public String getType() {
        return "LUXURY SUV (" + amenities + ")";
    }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
}
