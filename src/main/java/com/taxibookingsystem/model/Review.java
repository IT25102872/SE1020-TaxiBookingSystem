package com.taxibookingsystem.model;

public class Review {

    // Encapsulation — private fields
    private String reviewId;
    private String bookingId;
    private String passengerName;
    private int rating;       // 1 - 5
    private String comment;
    private String status;    // PENDING, APPROVED, REJECTED

    public Review(String reviewId, String bookingId, String passengerName,
                  int rating, String comment, String status) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
    }

    public Review() {}

    public String getReviewId()      { return reviewId; }
    public String getBookingId()     { return bookingId; }
    public String getPassengerName() { return passengerName; }
    public int getRating()           { return rating; }
    public String getComment()       { return comment; }
    public String getStatus()        { return status; }

    public void setReviewId(String reviewId)           { this.reviewId = reviewId; }
    public void setBookingId(String bookingId)         { this.bookingId = bookingId; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public void setRating(int rating)                  { this.rating = rating; }
    public void setComment(String comment)             { this.comment = comment; }
    public void setStatus(String status)               { this.status = status; }

    // File write — CSV format
    public String toFileString() {
        return reviewId + "," + bookingId + "," + passengerName + ","
                + rating + "," + comment + "," + status;
    }
}
