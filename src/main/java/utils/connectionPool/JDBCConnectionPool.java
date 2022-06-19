package utils.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPool {

    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final JDBCConfiguration config = JDBCConfiguration.getInstance();

    JDBCConnectionPool() {
        this.availableConnections = initialisePool();
    }


    private List<Connection> initialisePool() {
        List<Connection> connection = new ArrayList<>();
        for (int i = 0; i < config.getMaxConnections(); i++) {
            connection.add(createNewConnection());
        }
        return connection;
    }


    Connection getConnectionFromPool() {
        Connection connection = null;
        if(availableConnections.size() > 0) {
             connection = availableConnections.get(0);
             usedConnections.add(connection);
             availableConnections.remove(0); // почему remove(int) требует try-with-resources, а remove(Object) - нет?
        }
        return connection;
    }


    void releaseConnection(Connection connection){
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }


    private Connection createNewConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getURL(), config.getUserName(), config.getPassword());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}