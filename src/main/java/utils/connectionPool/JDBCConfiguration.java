package utils.connectionPool;

public class JDBCConfiguration {

    private static final JDBCConfiguration config = new JDBCConfiguration();
    private final String DB_USER_NAME;
    private final String DB_PASSWORD;
    private final String DB_URL;
    private final Integer DB_MAX_CONNECTION;

    private JDBCConfiguration(){
        DB_USER_NAME = "root";
        DB_PASSWORD = "Resufe05";
        DB_URL = "jdbc:mysql://localhost:3306/testdb";
        DB_MAX_CONNECTION = 10;
    }

    public static JDBCConfiguration getInstance(){
        return config;
    }

    public String getUserName() {
        return DB_USER_NAME;
    }

    public String getPassword() {
        return DB_PASSWORD;
    }

    public String getURL() {
        return DB_URL;
    }

    public Integer getMaxConnections() {
        return DB_MAX_CONNECTION;
    }
}
