package repository.impl;

import domain.LabelEntity;
import domain.PostEntity;
import repository.LabelRepository;
import utils.JDBCUtil;
import utils.connectionPool.DataSource;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCLabelRepositoryImpl implements LabelRepository {


    public static final String ALL_QUERY = "SELECT * FROM label";
    public static final String BY_ID_QUERY = "SELECT * FROM label WHERE id = ?";
    public static final String SAVE_QUERY = "INSERT IGNORE INTO label (name) VALUES (?)";
    public static final String UPDATE_QUERY = "UPDATE label SET name = ? WHERE id = ?";
    public static final String DELETE_QUERY = "DELETE FROM label WHERE id = ?";
    public final String GET_LABELS_BY_POST = "SELECT * FROM label WHERE id IN (SELECT label_id FROM post_label WHERE post_id = ?)";
    private final String ATTACH_LABELS_TO_POST = "INSERT IGNORE INTO post_label VALUES(?, ?)";
    private final String DETACH_ALL_LABELS = "DELETE FROM post_label WHERE post_id = ?";



    @Override
    public List<LabelEntity> findAll() throws SQLException {
        ArrayList<LabelEntity> labelEntities = new ArrayList<>();
        PreparedStatement statement = DataSource.getConnection().prepareStatement(ALL_QUERY);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            labelEntities.add(new LabelEntity(
                    result.getLong("id"),
                    result.getString("name"))
            );
        }
        DataSource.returnConnection(statement.getConnection());
        return labelEntities;
    }


    @Override
    public LabelEntity findById(Long id) throws SQLException {
        LabelEntity label;
        PreparedStatement statement = DataSource.getConnection().prepareStatement(BY_ID_QUERY);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if (result.next()) {
            label = new LabelEntity(result.getLong("id"), result.getString("name"));
            DataSource.returnConnection(statement.getConnection());
            return label;
        }
        else {
            DataSource.returnConnection(statement.getConnection());
            return null;
        }
    }

    @Override
    public LabelEntity save(LabelEntity labelEntity) throws SQLException{
        PreparedStatement statement = DataSource.getConnection().prepareStatement(SAVE_QUERY);
        statement.setString(1, labelEntity.getName());
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return labelEntity;
    }


    @Override
    public LabelEntity update(LabelEntity label) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(UPDATE_QUERY);
        statement.setString(1, label.getName());
        statement.setLong(2, label.getId());
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
        return label;
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(DELETE_QUERY);
        statement.setLong(1, id);
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
    }

    //Достаёт из БД и сохраняет в List все тэги, относящиеся к данному посту:
    @Override
    public List<LabelEntity> findAssociatedLabels(Long id) throws SQLException {
        ArrayList<LabelEntity> labels = new ArrayList<>();
        PreparedStatement statement = DataSource.getConnection().prepareStatement(GET_LABELS_BY_POST);
        statement.setLong(1, id);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            labels.add(new LabelEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"))
            );
        }
        DataSource.returnConnection(statement.getConnection());
        return labels;
    }


    //Сохраняет в связующую таблицу связь между Post и Label:
    @Override
    public List<LabelEntity> attachNewLabelsToPost(PostEntity newPost) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(ATTACH_LABELS_TO_POST);
        for(LabelEntity label : newPost.getLabels()){
            statement.setLong(1, newPost.getId());
            statement.setLong(2, label.getId());
            statement.execute();
        }
        DataSource.returnConnection(statement.getConnection());
        return newPost.getLabels();
    }

    @Override
    public void detachLabelsById(List<LabelEntity> labels, Long postId) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(JDBCUtil.sqlQueryToDetachLabels(labels));
        statement.setLong(1, postId);
        for (int i = 2; i <= labels.size()+1; i++) {
            statement.setLong(i, labels.get(i-2).getId());
        }
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
    }

    public void detachAllLabels(Long postId) throws SQLException {
        PreparedStatement statement = DataSource.getConnection().prepareStatement(DETACH_ALL_LABELS);
        statement.setLong(1, postId);
        statement.execute();
        DataSource.returnConnection(statement.getConnection());
    }
}


