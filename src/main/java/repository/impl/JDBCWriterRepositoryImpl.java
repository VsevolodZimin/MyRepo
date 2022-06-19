package repository.impl;

import domain.WriterEntity;
import repository.WriterRepository;
import service.PostService;
import utils.connectionPool.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCWriterRepositoryImpl implements WriterRepository {


    PostService pService;
    // 1. Контороллер - только знает о сервисе (сервис А, сервис Б ...)
    // 2. Сервис - только знает о репозитори ()
    // 3. Репозитори
    // 4. БД


    private final String GET_ALL_WRITERS = "SELECT * FROM writer";
    private final String GET_WRITER_BY_ID = "SELECT * FROM writer WHERE id = ?";
    private final String UPDATE_WRITER = "UPDATE writer SET firstName = ?, lastName = ? WHERE id = ?";
    private final String DELETE_WRITER = "DELETE FROM writer WHERE id = ?";
    private final String SAVE_WRITER = "INSERT INTO writer VALUES(?, ?, ?)";

    @Override
    public List<WriterEntity> findAll() throws SQLException {
        ArrayList<WriterEntity> writers = new ArrayList<>();
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_ALL_WRITERS);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            writers.add(new WriterEntity(
                    result.getLong("id"),
                    result.getString("firstName"),
                    result.getString("lastName")));
        }
        DataSource.returnConnection(statement.getConnection());
        return writers;
    }


    @Override
    public WriterEntity findById(Long id) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_WRITER_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if (result.next()) {
            WriterEntity writer = new WriterEntity(
                    result.getLong("id"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    null
            );
            DataSource.returnConnection(statement.getConnection());
            return writer;
        }
        DataSource.returnConnection(statement.getConnection());
        return null;
    }


    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(SAVE_WRITER);
        statement.setLong(1, writer.getId());
        statement.setString(2, writer.getFirstName());
        statement.setString(3, writer.getLastName());
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return writer;
    }


    @Override
    public WriterEntity update(WriterEntity writer) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(UPDATE_WRITER);
        statement.setString(1, writer.getFirstName());
        statement.setString(2, writer.getLastName());
        statement.setLong(3, writer.getId());statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return writer;
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(DELETE_WRITER);
        statement.setLong(1, id);
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        }
    }
