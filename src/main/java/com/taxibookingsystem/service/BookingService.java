package com.taxibookingsystem.service;

import java.io.*;
import java.util.*;

public class BookingService {

    // File name — data/bookings.txt
    private static final String FILE_PATH = "data/bookings.txt";

    // -------- CREATE --------
    public void createBooking(Booking booking) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(booking.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }

    // -------- READ — සියලු bookings --------
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        Booking b = new Booking(parts[0], parts[1], parts[2], parts[3]);
                        b.setStatus(parts[4]);
                        b.setFare(Double.parseDouble(parts[5]));
                        bookings.add(b);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    // -------- READ single --------
    public Booking getBookingById(String bookingId) {
        return getAllBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updateBookingStatus(String bookingId, String newStatus) {
        List<Booking> bookings = getAllBookings();
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                b.setStatus(newStatus);
                break;
            }
        }
        saveAllToFile(bookings);
    }

    // -------- DELETE --------
    public void deleteBooking(String bookingId) {
        List<Booking> bookings = getAllBookings();
        bookings.removeIf(b -> b.getBookingId().equals(bookingId));
        saveAllToFile(bookings);
    }

    // -------- Helper --------
    private void saveAllToFile(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Booking b : bookings) {
                writer.write(b.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
