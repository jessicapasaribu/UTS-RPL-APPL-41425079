package ehr;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionEHR {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:ehr.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}