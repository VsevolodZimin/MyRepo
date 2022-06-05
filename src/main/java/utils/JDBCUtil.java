package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    public static final String URL = "jdbc:mysql://localhost:3306/testdb";
    public static final String USER = "root";
    public static final String PASSWORD = "Resufe05";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
