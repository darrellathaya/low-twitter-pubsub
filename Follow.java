import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Follow {

    public static void main(String[] a) throws Exception {
        String email = a[0];
        String emailToFollow = a[1];

        // dapatkan tanggal sekarang
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        // melakukan koneksi ke sqlite3
        Class.forName("org.sqlite.JDBC");

        // Insert into centralized database (db1.sqlite3)
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:db1.sqlite3")) {
            String sql = "INSERT INTO follows(email,follow_created,email_to_follow) VALUES(?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, formattedDate);
            stmt.setString(3, emailToFollow);
            stmt.executeUpdate();
            stmt.close();
        }

        // Insert into pub/sub database (pubsub.sqlite3)
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3")) {
            String sql = "INSERT OR IGNORE INTO subscriptions(subscriber_email,publisher_email) VALUES(?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, emailToFollow);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
