package utils.connectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private FileInputStream input;
    private Properties properties;
    private String user;
    private String password;
    private String url;

    public DataSource() throws IOException {
        this.input = new FileInputStream("config.properties");
        this.properties = new Properties();
        properties.load(input);
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.url = properties.getProperty("url");
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
