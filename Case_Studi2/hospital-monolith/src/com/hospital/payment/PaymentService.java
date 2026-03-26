package com.hospital.payment;

public class PaymentService {

    public void processPayment(String user, int amount) {
        System.out.println("Payment $" + amount + " processed for " + user);
    }
}