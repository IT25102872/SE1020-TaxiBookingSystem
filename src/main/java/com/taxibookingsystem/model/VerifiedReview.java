package com.taxibookingsystem.model;

// Inheritance — VerifiedReview extends Review
public class VerifiedReview extends Review {

    private String verifiedBy;

    public VerifiedReview(String reviewId, String bookingId, String passengerName,
                          int rating, String comment, String verifiedBy) {
        super(reviewId, bookingId, passengerName, rating, comment, "APPROVED");
        this.verifiedBy = verifiedBy;
    }

    // Polymorphism — override getStatus()
    @Override
    public String getStatus() {
        return "APPROVED (by " + verifiedBy + ")";
    }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
}
