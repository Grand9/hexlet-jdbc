import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Application3 {
    public static void main(String[] args) {
        String url = "jdbc:h2:~/test";
        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {

            String insertSql = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, "Tommy");
                preparedStatement.setString(2, "33333333");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Maria");
                preparedStatement.setString(2, "44444444");
                preparedStatement.executeUpdate();

                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println("Inserted user with ID: " + generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB did not return an ID after inserting the entity");
                }
            }

            String deleteSql = "DELETE FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, "Tommy");
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("User 'Tommy' was deleted successfully.");
                } else {
                    System.out.println("No user found with the name 'Tommy'.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
