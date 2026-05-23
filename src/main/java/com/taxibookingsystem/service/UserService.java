package com.taxibookingsystem.service;

import com.taxibookingsystem.model.User;

import java.io.*;
import java.util.*;

public class UserService {

    // File name — data/users.txt
    private static final String FILE_PATH = "data/users.txt";

    // -------- CREATE --------
    public void createUser(User user) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // -------- READ — සියලු users --------
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    // -------- READ — ID ලෙස single user --------
    public User getUserById(String userId) {
        return getAllUsers().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updateUser(String userId, String email, String role) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                u.setEmail(email);
                u.setRole(role);
                break;
            }
        }
        saveAllToFile(users);
    }

    // -------- DELETE --------
    public void deleteUser(String userId) {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getUserId().equals(userId));
        saveAllToFile(users);
    }

    // -------- Helper --------
    private void saveAllToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (User u : users) {
                writer.write(u.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
