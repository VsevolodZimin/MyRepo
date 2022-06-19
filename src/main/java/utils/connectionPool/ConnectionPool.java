package utils.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    void createConnectionPoolContainer (DataSource dataSource, int poolCapacity, int maxConnection) throws SQLException;

    Connection getConnectionFromPool() throws SQLException;

    void retrieveConnection() throws SQLException;
}
