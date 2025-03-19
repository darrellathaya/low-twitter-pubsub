import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {

    public static void main(String[] a) throws Exception {
        // mengambil parameter
        String email = a[0];
        String title = a[1]; // New field: title
        String message = "";
        for (int i = 2; i < a.length; i++) {
            message += a[i] + " ";
        }

        // dapatkan tanggal sekarang
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        // melakukan koneksi ke sqlite3
        Class.forName("org.sqlite.JDBC");

        // Insert into centralized database (db1.sqlite3)
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:db1.sqlite3")) {
            String sql = "INSERT INTO posts(email,post_created,post) VALUES(?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, formattedDate);
            stmt.setString(3, message);
            stmt.executeUpdate();
            stmt.close();
        }

        // Insert into pub/sub database (pubsub.sqlite3)
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3")) {
            String sql = "INSERT INTO messages(email,title,post,post_created) VALUES(?,?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, title);
            stmt.setString(3, message);
            stmt.setString(4, formattedDate);
            stmt.executeUpdate();

            // Broadcast to all subscribers
            sql = "SELECT subscriber_email FROM subscriptions WHERE publisher_email=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String subscriberEmail = rs.getString(1);
                System.out.println("Broadcasting message to: " + subscriberEmail);
                // Here you could send notifications or perform other actions
            }
            rs.close();
            ps.close();
        }
    }
}
