package com.taxibookingsystem.service;

import java.io.*;
import java.util.*;

public class DriverService {

    // File name — data/drivers.txt
    private static final String FILE_PATH = "data/drivers.txt";

    // -------- CREATE --------
    public void createDriver(Driver driver) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(driver.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving driver: " + e.getMessage());
        }
    }

    // -------- READ --------
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        drivers.add(new Driver(parts[0], parts[1], parts[2], parts[3], parts[4]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading drivers: " + e.getMessage());
        }
        return drivers;
    }

    // -------- READ single --------
    public Driver getDriverById(String driverId) {
        return getAllDrivers().stream()
                .filter(d -> d.getDriverId().equals(driverId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updateDriver(String driverId, String phone, String status) {
        List<Driver> drivers = getAllDrivers();
        for (Driver d : drivers) {
            if (d.getDriverId().equals(driverId)) {
                d.setPhone(phone);
                d.setStatus(status);
                break;
            }
        }
        saveAllToFile(drivers);
    }

    // -------- DELETE --------
    public void deleteDriver(String driverId) {
        List<Driver> drivers = getAllDrivers();
        drivers.removeIf(d -> d.getDriverId().equals(driverId));
        saveAllToFile(drivers);
    }

    private void saveAllToFile(List<Driver> drivers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Driver d : drivers) {
                writer.write(d.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
