package ehr;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class EHRService {

    public static void main(String[] args) throws Exception {

        try (Connection conn = DBConnectionEHR.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS records (user TEXT, record TEXT)");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8003), 0);

        server.createContext("/record", exchange -> {
            try (Connection conn = DBConnectionEHR.connect()) {

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO records(user, record) VALUES(?,?)");
                stmt.setString(1, "john");
                stmt.setString(2, "Flu");
                stmt.executeUpdate();

                String response = "Record saved";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            }
        });

        server.start();
        System.out.println("EHR Service running (port 8003)");
    }
}