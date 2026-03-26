package com.hospital.ehr;

import com.hospital.database.DBConnection;
import java.sql.*;

public class EHRService {

    public void addRecord(String user, String record) {
        try {
            Connection conn = DBConnection.connect();
            String sql = "INSERT INTO records(user, record) VALUES(?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, record);
            stmt.executeUpdate();

            System.out.println("Medical record added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
