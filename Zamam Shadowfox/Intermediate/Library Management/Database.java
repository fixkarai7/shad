import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        try (Connection con = connect();
             Statement st = con.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS books(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                author TEXT,
                category TEXT,
                available INTEGER
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS borrowed(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                book_id INTEGER
                )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
