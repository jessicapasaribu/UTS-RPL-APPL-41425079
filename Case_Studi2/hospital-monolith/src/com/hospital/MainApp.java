package com.hospital;

import com.hospital.database.DBConnection;
import com.hospital.auth.AuthService;
import com.hospital.appointment.AppointmentService;
import com.hospital.ehr.EHRService;

import java.sql.Connection;
import java.sql.Statement;

public class MainApp {

    public static void main(String[] args) {

        // 1. INIT DATABASE (CREATE TABLE)
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (user TEXT, doctor TEXT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS records (user TEXT, record TEXT)");

            System.out.println("Database initialized!");

        } catch (Exception e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }

        // 2. INIT SERVICES (MONOLITH - semua dalam satu app)
        AuthService auth = new AuthService();
        AppointmentService appointment = new AppointmentService();
        EHRService ehr = new EHRService();

        // 3. SIMULASI FLOW SYSTEM
        System.out.println("\n=== SYSTEM START ===");

        // REGISTER USER
        auth.register("jessica", "1234");

        // LOGIN + BOOK APPOINTMENT (dependency ke Auth & Payment)
        appointment.bookAppointment("jessica", "1234");

        // ADD MEDICAL RECORD
        ehr.addRecord("jessica", "Flu diagnosis");

        System.out.println("=== SYSTEM END ===");
    }
}