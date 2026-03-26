package payment;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class PaymentService {

    public static void main(String[] args) throws Exception {

        try (Connection conn = DBConnectionPayment.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS payments (user TEXT, amount INT)");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8002), 0);

        server.createContext("/pay", exchange -> {
            try (Connection conn = DBConnectionPayment.connect()) {

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO payments(user, amount) VALUES(?,?)");
                stmt.setString(1, "john");
                stmt.setInt(2, 100);
                stmt.executeUpdate();

                String response = "Payment success";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            }
        });

        server.start();
        System.out.println("Payment Service running (port 8002)");
    }
}