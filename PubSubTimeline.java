import java.sql.*;

public class PubSubTimeline {

    public static void main(String[] a) throws Exception {
        String email = a[0];

        // melakukan koneksi ke sqlite3
        Class.forName("org.sqlite.JDBC");

        // Fetch messages from pub/sub database (pubsub.sqlite3)
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3")) {
            String sql = "SELECT m.email, m.title, m.post, m.post_created " +
                         "FROM messages m " +
                         "JOIN subscriptions s ON m.email = s.publisher_email " +
                         "WHERE s.subscriber_email = ? " +
                         "ORDER BY m.post_created DESC";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("User: " + rs.getString(1));
                System.out.println("Title: " + rs.getString(2));
                System.out.println("Message: " + rs.getString(3));
                System.out.println("Date: " + rs.getString(4));
                System.out.println();
            }

            rs.close();
            ps.close();
        }
    }
}
