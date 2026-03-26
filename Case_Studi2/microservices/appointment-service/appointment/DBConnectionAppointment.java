package appointment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionAppointment {

    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:appointment.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}