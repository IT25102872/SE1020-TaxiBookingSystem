package com.taxibookingsystem.service;

import com.taxibookingsystem.model.Vehicle;

import java.io.*;
import java.util.*;

public class VehicleService {

    // File name — data/vehicles.txt
    private static final String FILE_PATH = "data/vehicles.txt";

    // -------- CREATE --------
    public void createVehicle(Vehicle vehicle) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(vehicle.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    // -------- READ --------
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        vehicles.add(new Vehicle(parts[0], parts[1], parts[2],
                                parts[3], parts[4], parts[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    // -------- READ single --------
    public Vehicle getVehicleById(String vehicleId) {
        return getAllVehicles().stream()
                .filter(v -> v.getVehicleId().equals(vehicleId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updateVehicle(String vehicleId, String type, String status) {
        List<Vehicle> vehicles = getAllVehicles();
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equals(vehicleId)) {
                v.setType(type);
                v.setStatus(status);
                break;
            }
        }
        saveAllToFile(vehicles);
    }

    // -------- DELETE --------
    public void deleteVehicle(String vehicleId) {
        List<Vehicle> vehicles = getAllVehicles();
        vehicles.removeIf(v -> v.getVehicleId().equals(vehicleId));
        saveAllToFile(vehicles);
    }

    private void saveAllToFile(List<Vehicle> vehicles) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Vehicle v : vehicles) {
                writer.write(v.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
