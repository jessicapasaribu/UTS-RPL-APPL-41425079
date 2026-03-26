package pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionPharmacy {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}