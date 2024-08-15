package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {

    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet")) {

            var sql = "CREATE TABLE films (id BIGINT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(255), release_year INT, duration INT)";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO films (title, release_year, duration) VALUES "
                    + "('Godfather', 1972, 175), "
                    + "('The Green Mile', 1999, 189)";
            try (var statement2 = conn.createStatement()) {
                statement2.executeUpdate(sql2);
            }

            var sql3 = "SELECT * FROM films";
            try (var statement3 = conn.createStatement();
                 var resultSet = statement3.executeQuery(sql3)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("title") + " "
                            + resultSet.getInt("release_year") + " "
                            + resultSet.getInt("duration"));
                }
            }
        }
    }
}