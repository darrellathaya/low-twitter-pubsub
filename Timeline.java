import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timeline {

    public static void main(String[] a) throws Exception {
        // mengambil parameter
        String email = a[0];

        // melakukan koneksi ke sqlite3
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:db1.sqlite3");

        // select yang di follow oleh si email
        String sql = "SELECT email_to_follow FROM follows WHERE email=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // setiap di follow di select post nya
            String emailToFollow = rs.getString(1);
            sql = "SELECT post_created,post FROM posts WHERE email=?";
            PreparedStatement ps2 = c.prepareStatement(sql);
            ps2.setString(1, emailToFollow);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                // Default title for centralized posts
                String title = "No Title"; 
                System.out.println("User: " + emailToFollow);
                System.out.println("Title: " + title);
                System.out.println("Message: " + rs2.getString(2));
                System.out.println("Date: " + rs2.getString(1));
                System.out.println();
            }
            rs2.close();
            ps2.close();
        }

        // close connection
        rs.close();
        ps.close();
        c.close();
    }
}
