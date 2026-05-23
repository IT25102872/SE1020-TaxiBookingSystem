package com.taxibookingsystem.model;

public class Payment {

    // Encapsulation — private fields
    private String paymentId;
    private String bookingId;
    private String passengerName;
    private double amount;
    private String method; // CASH, CARD, ONLINE
    private String status; // PENDING, COMPLETED, FAILED

    public Payment(String paymentId, String bookingId, String passengerName,
                   double amount, String method, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public Payment() {}

    public String getPaymentId()     { return paymentId; }
    public String getBookingId()     { return bookingId; }
    public String getPassengerName() { return passengerName; }
    public double getAmount()        { return amount; }
    public String getMethod()        { return method; }
    public String getStatus()        { return status; }

    public void setPaymentId(String paymentId)         { this.paymentId = paymentId; }
    public void setBookingId(String bookingId)         { this.bookingId = bookingId; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public void setAmount(double amount)               { this.amount = amount; }
    public void setMethod(String method)               { this.method = method; }
    public void setStatus(String status)               { this.status = status; }

    public String toFileString() {
        return paymentId + "," + bookingId + "," + passengerName + ","
                + amount + "," + method + "," + status;
    }
}
