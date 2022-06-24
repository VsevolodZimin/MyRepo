package utils.connectionPool.impl;

import utils.connectionPool.DataSource;
import utils.connectionPool.ConnectionPool;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPool implements ConnectionPool {

    private static ConnectionPool connectionPoolContainer;
    private List<Connection> connectionPool;
    private DataSource dataSource;
    private static int defaultCapacity = 100;
    private static int currentCapacity = defaultCapacity;
    private static int maxCapacity = 150;
    private static int usedConnections = 0;


    private JDBCConnectionPool(DataSource dataSource, List<Connection> pool) {
        this.dataSource = dataSource;
        this.connectionPool = pool;
    }

    public static void initialise(DataSource dataSource) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        if (connectionPoolContainer == null) {
            for (int i = 0; i < defaultCapacity; i++) {
                connections.add(dataSource.createConnection());
            }
            connectionPoolContainer = new JDBCConnectionPool(dataSource, connections);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            if(connectionPool.size() > 0) {
                connection = connectionPool.get(0);
                connectionPool.remove(0); //Почему remove(int) требует try with recourses, а remove(object)
                usedConnections++;
            }
            else {
                if(currentCapacity <= maxCapacity){
                    connectionPool.add(dataSource.createConnection());
                    currentCapacity++;
                    connection = getConnection();
                }
            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void retrieveConnection() throws SQLException {
        connectionPool.add(dataSource.createConnection());
        usedConnections--;
        checkIfPoolShrinkable();
    }

    // Проверяет уменьшилась ли нагрузка на пул. Если да, то неиспользуемые соединения будут закрываться и удаляться из пула,
    // приводя его к изначальному размеру.
    private void checkIfPoolShrinkable() throws SQLException {

        synchronized (this){
            if(currentCapacity > defaultCapacity && usedConnections <= defaultCapacity){
                for(int i = 0; i < currentCapacity - defaultCapacity; i++){
                    connectionPool.get(0).close();
                    connectionPool.remove(0);
                }
            }
        }
    }

    public static ConnectionPool getPoolContainer() {
        return connectionPoolContainer;
    }
}

