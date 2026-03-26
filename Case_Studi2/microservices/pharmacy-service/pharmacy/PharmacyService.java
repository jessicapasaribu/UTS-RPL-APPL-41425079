package pharmacy;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class PharmacyService {

    public static void main(String[] args) throws Exception {

        try (Connection conn = DBConnectionPharmacy.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (user TEXT, medicine TEXT)");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8004), 0);

        server.createContext("/order", exchange -> {
            try (Connection conn = DBConnectionPharmacy.connect()) {

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO orders(user, medicine) VALUES(?,?)");
                stmt.setString(1, "john");
                stmt.setString(2, "Paracetamol");
                stmt.executeUpdate();

                String response = "Medicine ordered";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            }
        });

        server.start();
        System.out.println("Pharmacy Service running (port 8004)");
    }
}