package com.taxibookingsystem.service;

import com.taxibookingsystem.model.Booking;
import java.io.*;
import java.util.*;

public class BookingService {

    // bookings.txt file location
    private static final String FILE_PATH = "data/bookings.txt";

    // CREATE — Write new booking into file
    public void createBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {

            // true = append mode
            writer.write(booking.toFileString());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }

    // READ — Get all bookings from file
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    Booking b = new Booking(
                            parts[0], parts[1], parts[2], parts[3]);
                    b.setStatus(parts[4]);
                    b.setFare(Double.parseDouble(parts[5]));
                    bookings.add(b);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    // UPDATE — Update status from booking ID
    public void updateBookingStatus(String bookingId, String newStatus) {
        List<Booking> bookings = getAllBookings();

        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                b.setStatus(newStatus);
                break;
            }
        }

        // Again save the Updated list to file
        saveAllToFile(bookings);
    }

    // Helper method — Write the list into the file
    private void saveAllToFile(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {
            // false = overwrite mode — Rewrite the file
            for (Booking b : bookings) {
                writer.write(b.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating: " + e.getMessage());
        }
    }

    // DELETE — Remove the booking from booking ID
    public void deleteBooking(String bookingId) {
        List<Booking> bookings = getAllBookings();

        // Keep the bookings that does notmatch the booking ID
        bookings.removeIf(b ->
                b.getBookingId().equals(bookingId));

        // Save the rest booking files
        saveAllToFile(bookings);
    }

    // BONUS — Get the single bookings from ID
    public Booking getBookingById(String bookingId) {
        return getAllBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);
    }

}

