package com.hospital.appointment;

import com.hospital.auth.AuthService;
import com.hospital.payment.PaymentService;
import com.hospital.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AppointmentService {

    private AuthService authService = new AuthService();
    private PaymentService paymentService = new PaymentService();

    public void bookAppointment(String user, String password) {

        if (!authService.login(user, password)) {
            System.out.println("Login failed!");
            return;
        }

        paymentService.processPayment(user, 100);

        try (Connection conn = DBConnection.connect()) {

            String sql = "INSERT INTO appointments(user, doctor) VALUES(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, user);
            stmt.setString(2, "Dr. Smith");
            stmt.executeUpdate();

            System.out.println("Appointment booked!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}