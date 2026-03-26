package auth;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class AuthService {

    public static void main(String[] args) throws Exception {

        // INIT DB
        try (Connection conn = DBConnectionAuth.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // REGISTER
        server.createContext("/register", exchange -> {

            try (Connection conn = DBConnectionAuth.connect()) {

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT OR IGNORE INTO users(username, password) VALUES(?,?)");

                stmt.setString(1, "jessica");
                stmt.setString(2, "1234");
                stmt.executeUpdate();

                String response = "User registered";
                exchange.sendResponseHeaders(200, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // LOGIN
        server.createContext("/login", exchange -> {

            try (Connection conn = DBConnectionAuth.connect()) {

                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?");

                stmt.setString(1, "jessica");
                stmt.setString(2, "1234");

                ResultSet rs = stmt.executeQuery();

                String response = rs.next() ? "Login success" : "Login failed";

                exchange.sendResponseHeaders(200, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        server.start();
        System.out.println("Auth Service running (port 8001)");
    }
}