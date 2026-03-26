package payment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionPayment {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:payment.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}