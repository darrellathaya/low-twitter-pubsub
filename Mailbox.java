import spark.Spark;
import java.sql.*;

public class Mailbox {

    public static void main(String[] args) {
        // Set up SparkJava server
        Spark.port(8080);

        // Endpoint to fetch posts for a user's mailbox
        Spark.get("/mailbox/:email", (req, res) -> {
            String email = req.params(":email");
            StringBuilder response = new StringBuilder();

            // Connect to SQLite
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:db1.sqlite3");

            // Fetch posts for users being followed
            String sql = "SELECT p.title, p.post, p.post_created, f.email_to_follow " +
                         "FROM follows f " +
                         "JOIN posts p ON f.email_to_follow = p.email " +
                         "WHERE f.email = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // Build JSON response
            response.append("[");
            while (rs.next()) {
                response.append("{");
                response.append("\"title\":\"").append(rs.getString("title")).append("\",");
                response.append("\"message\":\"").append(rs.getString("post")).append("\",");
                response.append("\"date\":\"").append(rs.getString("post_created")).append("\",");
                response.append("\"user\":\"").append(rs.getString("email_to_follow")).append("\"");
                response.append("},");
            }
            if (response.charAt(response.length() - 1) == ',') {
                response.deleteCharAt(response.length() - 1); // Remove trailing comma
            }
            response.append("]");

            // Close resources
            rs.close();
            ps.close();
            c.close();

            res.type("application/json");
            return response.toString();
        });
    }
}
