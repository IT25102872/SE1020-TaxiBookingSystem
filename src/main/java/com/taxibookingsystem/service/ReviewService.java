package com.taxibookingsystem.service;

import java.io.*;
import java.util.*;

public class ReviewService {

    // File name — data/reviews.txt
    private static final String FILE_PATH = "data/reviews.txt";

    // -------- CREATE --------
    public void createReview(Review review) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(review.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving review: " + e.getMessage());
        }
    }

    // -------- READ --------
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 6);
                    if (parts.length >= 6) {
                        reviews.add(new Review(parts[0], parts[1], parts[2],
                                Integer.parseInt(parts[3]), parts[4], parts[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews: " + e.getMessage());
        }
        return reviews;
    }

    // -------- READ single --------
    public Review getReviewById(String reviewId) {
        return getAllReviews().stream()
                .filter(r -> r.getReviewId().equals(reviewId))
                .findFirst().orElse(null);
    }

    // -------- UPDATE --------
    public void updateReview(String reviewId, String comment, String status) {
        List<Review> reviews = getAllReviews();
        for (Review r : reviews) {
            if (r.getReviewId().equals(reviewId)) {
                r.setComment(comment);
                r.setStatus(status);
                break;
            }
        }
        saveAllToFile(reviews);
    }

    // -------- DELETE --------
    public void deleteReview(String reviewId) {
        List<Review> reviews = getAllReviews();
        reviews.removeIf(r -> r.getReviewId().equals(reviewId));
        saveAllToFile(reviews);
    }

    private void saveAllToFile(List<Review> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Review r : reviews) {
                writer.write(r.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
