package com.hospital.auth;

import com.hospital.database.DBConnection;
import java.sql.*;

public class AuthService {

    // LOGIN (CHECK USER)
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login success!");
                return true;
            } else {
                System.out.println("Login failed!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //REGISTER (INSERT USER)
    public void register(String username, String password) {
        String sql = "INSERT OR IGNORE INTO users(username, password) VALUES(?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            System.out.println("User registered");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}