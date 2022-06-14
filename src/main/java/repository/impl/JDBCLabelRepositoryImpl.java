package repository.impl;

import domain.LabelEntity;
import domain.PostEntity;
import repository.LabelRepository;
import utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCLabelRepositoryImpl implements LabelRepository {


    public static final String ALL_QUERY = "SELECT * FROM label";
    public static final String BY_ID_QUERY = "SELECT * FROM label WHERE label_id = ?";
    public static final String SAVE_QUERY = "INSERT IGNORE INTO label VALUES (?)";
    public static final String UPDATE_QUERY = "UPDATE label SET name = ? WHERE label_id = ?";
    public static final String DELETE_QUERY = "DELETE FROM label WHERE label_id = ?";
    public final String GET_LABELS_BY_POST = "SELECT * FROM label WHERE label_id IN (SELECT label_id FROM post_label WHERE post_id = ?)";
    private final String ATTACH_LABELS_TO_POST = "INSERT IGNORE INTO post_label VALUES(?, ?)";
    private final String DETACH_ALL_LABELS = "DELETE FROM post_label WHERE post_id = ?";



    @Override
    public List<LabelEntity> findAll() throws SQLException {
        ArrayList<LabelEntity> labelEntities = new ArrayList<>();
        try (PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(ALL_QUERY)) {
            statement.execute();
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                labelEntities.add(new LabelEntity(
                        result.getLong("label_id"),
                        result.getString("name"))
                );
            }
        }
        return labelEntities;
    }


    @Override
    public LabelEntity findById(Long id) throws SQLException{

        LabelEntity label;
        try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(BY_ID_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            label = new LabelEntity(result.getLong("label_id"), result.getString("name"));
        }
        return label;
    }

    @Override
    public LabelEntity save(LabelEntity labelEntity) throws SQLException{
        try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(SAVE_QUERY)){
        statement.setLong(1, labelEntity.getId());
        statement.setString(2, labelEntity.getName());
        statement.execute();
        }
        return labelEntity;
    }


    @Override
    public LabelEntity update(LabelEntity label) throws SQLException {
        try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, label.getName());
            statement.setLong(2, label.getId());
            statement.execute();
            return label;
        }
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        try (PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    //Достаёт из БД и сохраняет в List все тэги, относящиеся к данному посту:
    @Override
    public List<LabelEntity> findAssociatedLabels(Long id) throws SQLException {
        ArrayList<LabelEntity> labels = new ArrayList<>();
        try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(GET_LABELS_BY_POST)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                labels.add(new LabelEntity(
                        resultSet.getLong("label_id"),
                        resultSet.getString("name"))
                );
            }
        }
        return labels;
    }


    //Сохраняет в связующую таблицу связь между Post и Label:
    @Override
    public List<LabelEntity> attachNewLabelsToPost(PostEntity newPost) throws SQLException {
        for(LabelEntity i : newPost.getLabels()){
            try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(ATTACH_LABELS_TO_POST)){
                statement.setLong(1, newPost.getId());
                statement.setLong(2, i.getId());
                statement.execute();
            }
        }
        return newPost.getLabels();
    }

    @Override
    public void detachLabelsById(List<LabelEntity> labels, Long postId) throws SQLException {
        try (PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(JDBCUtil.detachLabelString(labels))) {
            for (int i = 1; i <= labels.size(); i++) {
                statement.setLong(i, labels.get(i-1).getId());
            }
            statement.execute();
        }
    }

    public void detachAllLabels(Long postId) throws SQLException {
        try(PreparedStatement statement = JDBCUtil.getConnection().prepareStatement(DETACH_ALL_LABELS)){
            statement.setLong(1, postId);
            statement.execute();
        }
    }
}

