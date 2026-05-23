package com.taxibookingsystem.model;

// Inheritance — OnlinePayment extends Payment
public class OnlinePayment extends Payment {

    private String transactionId;

    public OnlinePayment(String paymentId, String bookingId, String passengerName,
                         double amount, String transactionId) {
        super(paymentId, bookingId, passengerName, amount, "ONLINE", "PENDING");
        this.transactionId = transactionId;
    }

    // Polymorphism — override getMethod()
    @Override
    public String getMethod() {
        return "ONLINE (TXN: " + transactionId + ")";
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
