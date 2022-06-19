package repository.impl;

import domain.PostEntity;
import repository.PostRepository;
import utils.connectionPool.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostRepositoryImpl implements PostRepository {

    private final String GET_ALL_POSTS = "SELECT * FROM post";
    private final String GET_POST_BY_ID = "SELECT * FROM post WHERE id = ?";
    private final String UPDATE_BY_ID = "UPDATE post SET content = ?, created = ?, updated = ? WHERE id = ?";
    private final String SAVE = "INSERT INTO post (id, content, created, updated, writer_id) VALUES(?, ?, ?, ?, ?)";
    private final String DELETE_BY_ID = "DELETE FROM post WHERE id = ?";
    private final String GET_POSTS_BY_WRITER = "SELECT id FROM post WHERE writer_id = ?";


    @Override
    public List<PostEntity> findAll() throws SQLException {
        ArrayList<PostEntity> posts = new ArrayList<>();
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_ALL_POSTS);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            posts.add(new PostEntity(
                    result.getLong("id"),
                    result.getString("content"),
                    (Date) result.getObject("created"),
                    (Date) result.getObject("updated"),
                    null,
                    result.getLong("writer_id")));
        }
        DataSource.returnConnection(statement.getConnection());
        return posts;
    }


    @Override
    public PostEntity findById(Long id) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_POST_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if (result.next()) {
            PostEntity post = new PostEntity(
                    result.getLong("id"),
                    result.getString("content"),
                    (Date) result.getObject("created"),
                    (Date) result.getObject("updated"),
                    null,
                    result.getLong("writer_id")
            );
            DataSource.returnConnection(statement.getConnection());
            return post;
        }
        DataSource.returnConnection(statement.getConnection());
    return null;
    }

    @Override
    public PostEntity save (PostEntity post) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(SAVE);
        statement.setLong(1, post.getId());
        statement.setString(2, post.getContent());
        statement.setObject(3, post.getCreated());
        statement.setObject(4, post.getUpdated());
        statement.setLong(5, post.getWriterId());
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return post;
    }

    @Override
    public PostEntity update(PostEntity post) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(UPDATE_BY_ID);
        statement.setString(1, post.getContent());
        statement.setObject(2, post.getCreated());
        statement.setObject(3, post.getUpdated());
        statement.setLong(4, post.getId());
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return post;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(DELETE_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
    }


    @Override
    public List<PostEntity> findAssociatedPostsByWriterId (Long writerId) throws SQLException {
        List<PostEntity> posts = new ArrayList<>();
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_POSTS_BY_WRITER);
        statement.setLong(1, writerId);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            posts.add(findById(result.getLong("id")));

        }
        DataSource.returnConnection(statement.getConnection());
        return posts;
    }
}