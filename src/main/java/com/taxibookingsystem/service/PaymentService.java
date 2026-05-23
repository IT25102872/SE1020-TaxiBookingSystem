package com.taxibookingsystem.service;

import java.io.*;
import java.util.*;

public class PaymentService {

    // File name — data/payments.txt
    private static final String FILE_PATH = "data/payments.txt";

    // -------- CREATE --------
    public void createPayment(Payment payment) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(payment.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving payment: " + e.getMessage());
        }
    }

    // -------- READ --------
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        payments.add(new Payment(parts[0], parts[1], parts[2],
                                Double.parseDouble(parts[3]), parts[4], parts[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading payments: " + e.getMessage());
        }
        return payments;
    }

    // -------- READ single --------
    public Payment getPaymentById(String paymentId) {
        return getAllPayments().stream()
                .filter(p -> p.getPaymentId().equals(paymentId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updatePayment(String paymentId, String method, String status) {
        List<Payment> payments = getAllPayments();
        for (Payment p : payments) {
            if (p.getPaymentId().equals(paymentId)) {
                p.setMethod(method);
                p.setStatus(status);
                break;
            }
        }
        saveAllToFile(payments);
    }

    // -------- DELETE --------
    public void deletePayment(String paymentId) {
        List<Payment> payments = getAllPayments();
        payments.removeIf(p -> p.getPaymentId().equals(paymentId));
        saveAllToFile(payments);
    }

    private void saveAllToFile(List<Payment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Payment p : payments) {
                writer.write(p.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
