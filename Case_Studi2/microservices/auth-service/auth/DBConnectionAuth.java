package auth;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionAuth {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:auth.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}