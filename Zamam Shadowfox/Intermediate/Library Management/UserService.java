import java.sql.*;

public class UserService {

    public static boolean register(String user, String pass) {
        String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        try (Connection c = Database.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean login(String user, String pass) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection c = Database.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user);
            String hashed = PasswordUtil.hashPassword(pass);
            ps.setString(2, hashed);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }
}
