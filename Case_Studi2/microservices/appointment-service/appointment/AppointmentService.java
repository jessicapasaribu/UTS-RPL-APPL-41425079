package appointment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

public class AppointmentService {

    public static void main(String[] args) throws Exception {

        // INIT DB
        try (Connection conn = DBConnectionAppointment.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (user TEXT, doctor TEXT)");
        }

        System.out.println("Booking appointment...");

        // CALL AUTH
        URL authUrl = new URL("http://localhost:8001/login");
        HttpURLConnection authConn = (HttpURLConnection) authUrl.openConnection();
        authConn.setRequestMethod("GET");

        BufferedReader authIn = new BufferedReader(
                new InputStreamReader(authConn.getInputStream()));
        System.out.println("Auth: " + authIn.readLine());

        // CALL PAYMENT
        URL payUrl = new URL("http://localhost:8002/pay");
        HttpURLConnection payConn = (HttpURLConnection) payUrl.openConnection();
        payConn.setRequestMethod("GET");

        BufferedReader payIn = new BufferedReader(
                new InputStreamReader(payConn.getInputStream()));
        System.out.println("Payment: " + payIn.readLine());

        // SAVE APPOINTMENT
        try (Connection conn = DBConnectionAppointment.connect()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO appointments(user, doctor) VALUES(?,?)");
            stmt.setString(1, "jessica");
            stmt.setString(2, "Dr. Febiola");
            stmt.executeUpdate();
        }

        System.out.println("Appointment booked!");
    }
}