package com.hospital.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:hospital.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}