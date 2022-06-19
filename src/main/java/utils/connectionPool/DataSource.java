package utils.connectionPool;

import java.sql.Connection;

public class DataSource {
    private static JDBCConnectionPool pool = new JDBCConnectionPool();

    public static Connection getConnection() {
        return pool.getConnectionFromPool();
    }

    public static void returnConnection(Connection connection){
        pool.releaseConnection(connection);
    }
}
