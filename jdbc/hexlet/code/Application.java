package io.hexlet;

import hexlet.code.User;
import hexlet.code.UserDao;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet")) {
            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var userDao = new UserDao(conn);

            // Сохранение нового пользователя
            var user = new User("Maria", "888888888");
            userDao.save(user);
            System.out.println("Saved user with ID: " + user.getId());

            // Поиск пользователя по ID
            var foundUser = userDao.find(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("Found user: " + foundUser.getName());

            // Удаление пользователя по ID
            userDao.delete(foundUser.getId());
            System.out.println("User deleted");

            // Попытка найти удаленного пользователя
            var userDeleted = userDao.find(foundUser.getId());
            System.out.println("User exists after deletion? " + userDeleted.isPresent());
        }
    }
}
