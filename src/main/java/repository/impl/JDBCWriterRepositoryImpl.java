package repository.impl;

import domain.WriterEntity;
import repository.WriterRepository;
import service.PostService;
import service.impl.PostServiceImpl;
import utils.JDBCUtil;
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
    private final String GET_WRITER_BY_ID = "SELECT * FROM writer WHERE writer_id = ?";
    private final String UPDATE_WRITER = "UPDATE writer SET firstName = ?, lastName = ? WHERE writer_id = ?";
    private final String DELETE_WRITER = "DELETE FROM writer WHERE writer_id = ?";
    private final String SAVE_WRITER = "INSERT INTO writer VALUES(?, ?, ?)";

    @Override
    public List<WriterEntity> findAll() throws SQLException {
        ArrayList<WriterEntity> writers = new ArrayList<>();
        PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(GET_ALL_WRITERS);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            writers.add(new WriterEntity(
                    result.getLong("writer_id"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    null
            ));
        }
        return writers;
    }


    @Override
    public WriterEntity findById(Long id) throws SQLException {
        PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(GET_WRITER_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();

        if (result.next()) {
            return new WriterEntity(
                    result.getLong("writer_id"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    null
            );
        }
        return null;
    }


    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(SAVE_WRITER);
        statement.setLong(1, writer.getId());
        statement.setString(2, writer.getFirstName());
        statement.setString(3, writer.getLastName());
        statement.execute();
        return writer;
    }


    @Override
    public WriterEntity update(WriterEntity writer) throws SQLException {
        PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(UPDATE_WRITER);
        statement.setString(1, writer.getFirstName());
        statement.setString(2, writer.getLastName());
        statement.setLong(3, writer.getId());
        statement.execute();
        return writer;
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(DELETE_WRITER);
        statement.setLong(1, id);
        statement.execute();
    }
}
