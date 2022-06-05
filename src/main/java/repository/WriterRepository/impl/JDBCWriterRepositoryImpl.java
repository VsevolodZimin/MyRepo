package repository.WriterRepository.impl;

import domain.WriterEntity;
import repository.WriterRepository.WriterRepository;
import service.PostService.PostService;
import service.PostService.PostServiceImpl;
import utils.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCWriterRepositoryImpl implements WriterRepository {

    PostService postService = new PostServiceImpl();
    Connection connection = JDBCUtil.getConnection();

    private final String GET_ALL_WRITERS = "SELECT * FROM writer";
    private final String GET_WRITER_BY_ID = "SELECT * FROM writer WHERE writer_id = ?";
    private final String UPDATE_WRITER = "UPDATE writer SET firstName = ?, lastName = ? WHERE writer_id = ?";
    private final String DELETE_WRITER = "DELETE FROM writer WHERE writer_id = ?";
    private final String SAVE_WRITER = "INSERT INTO writer VALUES(?, ?, ?)";

    public JDBCWriterRepositoryImpl() throws SQLException {
    }


    @Override
    public List<WriterEntity> findAll() throws SQLException {
        ArrayList<WriterEntity> writers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_WRITERS);
        statement.execute();
        ResultSet result = statement.getResultSet();

        while (result.next()) {
            writers.add(new WriterEntity(
                    result.getLong("writer_id"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    postService.findAssociatedPosts(result.getLong("writer_id"))
            ));
        }
        return writers;
    }

    @Override
    public WriterEntity findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_WRITER_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();

        if (result.next()) {
            return new WriterEntity(
                    result.getLong("writer_id"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    postService.findAssociatedPosts(result.getLong("writer_id"))
            );
        }
        return null;
    }

    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SAVE_WRITER);
        statement.setLong(1, writer.getId());
        statement.setString(2, writer.getFirstName());
        statement.setString(3, writer.getLastName());
        statement.execute();
        return writer;
    }

    @Override
    public WriterEntity update(WriterEntity writer, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_WRITER);
        statement.setString(1, writer.getFirstName());
        statement.setString(2, writer.getLastName());
        statement.setLong(3, id);
        statement.execute();
        return writer;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_WRITER);
        statement.setLong(1, id);
        statement.execute();
    }
}
