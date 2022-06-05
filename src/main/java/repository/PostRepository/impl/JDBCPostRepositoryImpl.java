package repository.PostRepository.impl;

import domain.LabelEntity;
import domain.PostEntity;
import repository.PostRepository.PostRepository;
import service.LabelService.LabelService;
import service.LabelService.impl.LabelServiceImpl;
import utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class JDBCPostRepositoryImpl implements PostRepository {

    LabelService labelService = new LabelServiceImpl();
    Connection connection = JDBCUtil.getConnection();

    public JDBCPostRepositoryImpl() throws SQLException {
    }

    private final String GET_ALL_POSTS = "SELECT * FROM post";
    private final String GET_POST_BY_ID = "SELECT * FROM post WHERE post_id = ?";
    private final String UPDATE_BY_ID = "UPDATE post SET content = ?, created = ?, updated = ? WHERE post_id = ?";
    private final String SAVE = "INSERT INTO post VALUES(?, ?, ?, ?)";
    private final String DELETE_BY_ID = "DELETE FROM post WHERE post_id = ?";
    private final String DETACH_POSTS_FROM_WRITER = "DELETE FROM posts_by_writer WHERE writer_id = ?";
    private final String GET_POSTS_BY_WRITER = "SELECT post_id FROM posts_by_writer WHERE writer_id = ?";
    private final String ATTACH_POST_TO_WRITER = "INSERT IGNORE INTO posts_by_writer VALUES(?, ?)";


    @Override
    public List<PostEntity> findAll() throws SQLException {
        ArrayList<PostEntity> posts = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(GET_ALL_POSTS);
        statement.execute();
        ResultSet result = statement.getResultSet();

        while(result.next()){
            posts.add(new PostEntity(
                    result.getLong("post_id"),
                    result.getString("content"),
                    (Date) result.getObject("created"),
                    (Date) result.getObject("updated"),
                    labelService.findAssociatedLabels(result.getLong("post_id"))));
        }
        return posts;
    }

    @Override
    public PostEntity findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_POST_BY_ID);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if (result.next()){
            return new PostEntity(
                    result.getLong("post_id"),
                    result.getString("content"),
                    (Date) result.getObject("created"),
                    (Date) result.getObject("updated"),
                    labelService.findAssociatedLabels(result.getLong("post_id")));
        }
        return null;
    }

    @Override
    public PostEntity save(PostEntity post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SAVE);
        statement.setLong(1, post.getId());
        statement.setString(2, post.getContent());
        statement.setObject(3, post.getCreated());
        statement.setObject(4, post.getUpdated());
        statement.execute();
        List<LabelEntity> labels = post.getLabels();

        //привязываю тэги к посту
        labelService.attachLabelsToPost(labels, post.getId());
        return post;
    }

    public PostEntity save(PostEntity post, Long writerId) throws SQLException {
        save(post);
        attachPostToWriter(post, writerId);
        return post;
    }

    @Override
    public PostEntity update(PostEntity post, Long id) throws SQLException {

        //обновляю пост в БД
        PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);
        statement.setString(1, post.getContent());
        statement.setObject(2, post.getCreated());
        statement.setObject(3, post.getUpdated());
        statement.setLong(4, id);
        statement.execute();

        //Обновляю список прикреплённых к посту тэгов
        labelService.updateLabelsByPost(post.getLabels(), id);
    return post;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
        statement.setLong(1, id);
        statement.execute();
    }

    @Override
    //ищу посты, связанные с данным писателем
    public List<PostEntity> findAssociatedPosts(Long writerId) throws SQLException {
        ArrayList<PostEntity> posts = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(GET_POSTS_BY_WRITER);
        statement.setLong(1, writerId);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while(result.next()) {
            posts.add(findById(result.getLong("post_id")));
        }
        return posts;
    }

    @Override
    public void attachPostToWriter(PostEntity post, Long writerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ATTACH_POST_TO_WRITER);
        statement.setLong(1, writerId);
        statement.setLong(2, post.getId());
        statement.execute();
    }
}

