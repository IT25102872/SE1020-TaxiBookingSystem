package com.taxibookingsystem.model;

public class Vehicle {

    // Encapsulation — private fields
    private String vehicleId;
    private String plateNumber;
    private String brand;
    private String model;
    private String type;   // CAR, VAN, SUV, BIKE, THREE_WHEEL
    private String status; // AVAILABLE, IN_USE, MAINTENANCE

    public Vehicle(String vehicleId, String plateNumber, String brand,
                   String model, String type, String status) {
        this.vehicleId = vehicleId;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.status = status;
    }

    public Vehicle() {}

    public String getVehicleId()   { return vehicleId; }
    public String getPlateNumber() { return plateNumber; }
    public String getBrand()       { return brand; }
    public String getModel()       { return model; }
    public String getType()        { return type; }
    public String getStatus()      { return status; }

    public void setVehicleId(String vehicleId)     { this.vehicleId = vehicleId; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setBrand(String brand)             { this.brand = brand; }
    public void setModel(String model)             { this.model = model; }
    public void setType(String type)               { this.type = type; }
    public void setStatus(String status)           { this.status = status; }

    public String toFileString() {
        return vehicleId + "," + plateNumber + "," + brand + ","
                + model + "," + type + "," + status;
    }
}
