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
    private int defaultCapacity;
    private int currentCapacity;
    private int maxCapacity;
    private int usedConnections;


    private JDBCConnectionPool(DataSource dataSource, List<Connection> pool, int poolCapacity, int maxCapacity) throws SQLException {
        this.dataSource = dataSource;
        this.connectionPool = pool;
        this.defaultCapacity = poolCapacity;
        this.currentCapacity = defaultCapacity;
        this.maxCapacity = maxCapacity;
        this.usedConnections = 0;
    }

    @Override
    public void createConnectionPoolContainer (DataSource dataSource, int poolCapacity, int maxConnection) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        if (connectionPoolContainer == null) {
            for (int i = 0; i < defaultCapacity; i++) {
                connections.add(dataSource.createConnection());
            }
            connectionPoolContainer = new JDBCConnectionPool(dataSource, connections, poolCapacity, maxConnection);
        }
    }

    @Override
    public Connection getConnectionFromPool() throws SQLException {
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
                    connection = getConnectionFromPool();
                    usedConnections++;
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
        if(currentCapacity > defaultCapacity && usedConnections <= defaultCapacity){
            for(int i = 0; i < currentCapacity - defaultCapacity; i++){
                connectionPool.get(0).close();
                connectionPool.remove(0);
            }
        }
    }
}

