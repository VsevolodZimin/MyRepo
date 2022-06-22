package repository.impl;

import domain.WriterEntity;
import repository.WriterRepository;
import service.PostService;
import utils.connectionPool.DataSource;
import utils.connectionPool.impl.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCWriterRepositoryImpl implements WriterRepository {

    private final String GET_ALL_WRITERS = "SELECT * FROM writer";
    private final String GET_WRITER_BY_ID = "SELECT * FROM writer WHERE id = ?";
    private final String UPDATE_WRITER = "UPDATE writer SET firstName = ?, lastName = ? WHERE id = ?";
    private final String DELETE_WRITER = "DELETE FROM writer WHERE id = ?";
    private final String SAVE_WRITER = "INSERT INTO writer VALUES(?, ?, ?)";

    @Override
    public List<WriterEntity> findAll() throws SQLException {
        ArrayList<WriterEntity> writers = new ArrayList<>();
        try(PreparedStatement statement = JDBCConnectionPool.getPoolContainer().getConnection().prepareStatement(GET_ALL_WRITERS)) {
            statement.execute();
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                writers.add(new WriterEntity(
                        result.getLong("id"),
                        result.getString("firstName"),
                        result.getString("lastName")));
            }
            return writers;
        }
        finally{
            JDBCConnectionPool.getPoolContainer().retrieveConnection();
        }
    }


    @Override
    public WriterEntity findById(Long id) throws SQLException {
        try(PreparedStatement statement = JDBCConnectionPool.getPoolContainer().getConnection().prepareStatement(GET_WRITER_BY_ID)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                WriterEntity writer = new WriterEntity(
                        result.getLong("id"),
                        result.getString("firstName"),
                        result.getString("lastName")
                );
                return writer;
            }
            return null;
        }
        finally {
            JDBCConnectionPool.getPoolContainer().retrieveConnection();
        }
    }


    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        try (PreparedStatement statement = JDBCConnectionPool.getPoolContainer().getConnection().prepareStatement(SAVE_WRITER)) {
            statement.setLong(1, writer.getId());
            statement.setString(2, writer.getFirstName());
            statement.setString(3, writer.getLastName());
            statement.execute();
            return writer;
        }
        finally {
            JDBCConnectionPool.getPoolContainer().retrieveConnection();
        }
    }


    @Override
    public WriterEntity update(WriterEntity writer) throws SQLException {
       try (PreparedStatement statement = JDBCConnectionPool.getPoolContainer().getConnection().prepareStatement(UPDATE_WRITER)) {
           statement.setString(1, writer.getFirstName());
           statement.setString(2, writer.getLastName());
           statement.setLong(3, writer.getId());
           statement.execute();
           return writer;
       }
       finally{
           JDBCConnectionPool.getPoolContainer().retrieveConnection();
       }
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        try(PreparedStatement statement = JDBCConnectionPool.getPoolContainer().getConnection().prepareStatement(DELETE_WRITER)) {
            statement.setLong(1, id);
            statement.execute();
        }
        finally {
            JDBCConnectionPool.getPoolContainer().retrieveConnection();
        }
    }
}
