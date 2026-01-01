import java.sql.*;
import java.util.*;

public class BookService {

    public static List<String> getAllBooks() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT id, title, available FROM books";

        try (Connection c = Database.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String status = rs.getInt("available") == 1 ? "Available" : "Borrowed";
                list.add(rs.getInt("id") + " - " + rs.getString("title") + " (" + status + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void borrowBook(int bookId, String user) {
        try (Connection c = Database.connect()) {

            c.prepareStatement(
                "UPDATE books SET available=0 WHERE id=? AND available=1"
            ).executeUpdate();

            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO borrowed(username, book_id) VALUES (?,?)"
            );
            ps.setString(1, user);
            ps.setInt(2, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(int bookId) {
        try (Connection c = Database.connect()) {
            c.prepareStatement(
                "UPDATE books SET available=1 WHERE id=?"
            ).executeUpdate();

            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM borrowed WHERE book_id=?"
            );
            ps.setInt(1, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
