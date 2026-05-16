package com.taxibookingsystem.model;

public class Driver {

    // Encapsulation — private fields
    private String driverId;
    private String name;
    private String phone;
    private String licenseNumber;
    private String status; // AVAILABLE, BUSY, OFFLINE

    public Driver(String driverId, String name, String phone,
                  String licenseNumber, String status) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }

    public Driver() {}

    public String getDriverId()      { return driverId; }
    public String getName()          { return name; }
    public String getPhone()         { return phone; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getStatus()        { return status; }

    public void setDriverId(String driverId)           { this.driverId = driverId; }
    public void setName(String name)                   { this.name = name; }
    public void setPhone(String phone)                 { this.phone = phone; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public void setStatus(String status)               { this.status = status; }

    public String toFileString() {
        return driverId + "," + name + "," + phone + "," + licenseNumber + "," + status;
    }
}
