package repository.LabelRepository.impl;

import domain.LabelEntity;
import repository.LabelRepository.LabelRepository;
import utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class JDBCLabelRepositoryImpl implements LabelRepository {

    Connection connection = JDBCUtil.getConnection();

    public static final String ALL_QUERY = "SELECT * FROM label";
    public static final String BY_ID_QUERY = "SELECT * FROM label WHERE label_id = ?";
    public static final String SAVE_QUERY = "INSERT IGNORE INTO label VALUES (?, ?)";
    public static final String UPDATE_QUERY = "UPDATE label SET name = ? WHERE label_id = ?";
    public static final String DELETE_QUERY = "DELETE FROM label WHERE label_id = ?";
    public final String GET_LABELS_BY_POST = "SELECT * FROM label WHERE label_id IN (SELECT label_id FROM post_label WHERE post_id = ?)";
    private final String ATTACH_LABELS_TO_POST = "INSERT IGNORE INTO labeled_post VALUES(?, ?)";
    private final String DETACH_ALL_LABELS = "DELETE FROM post_label WHERE post_id = ?";

    public JDBCLabelRepositoryImpl() throws SQLException {
    }

    @Override
    public List<LabelEntity> findAll() throws SQLException {
        ArrayList<LabelEntity> labelEntities = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            labelEntities.add(new LabelEntity(
                    result.getLong("label_id"),
                    result.getString("name"))
                );
        }
        return labelEntities;
    }


    @Override
    public LabelEntity findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(BY_ID_QUERY);
        statement.setLong(1, id);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if(result.next()){
            return new LabelEntity(
                    result.getLong("label_id"),
                    result.getString("name"));
        }
        return null;
    }


    @Override
    public LabelEntity save(LabelEntity labelEntity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SAVE_QUERY);
        statement.setLong(1, labelEntity.getId());
        statement.setString(2, labelEntity.getName());
        statement.execute();
        return labelEntity;
    }


    @Override
    public LabelEntity update(LabelEntity labelEntity, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setString(1, labelEntity.getName());
        statement.setLong(2, id);
        statement.execute();
        return labelEntity;
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
        statement.setLong(1, id);
        statement.execute();
    }

    @Override
    public List<LabelEntity> findAssociatedLabels(Long id) throws SQLException {
        ArrayList<LabelEntity> labels = new ArrayList<>();
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_LABELS_BY_POST);
        statement.setLong(1, id);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            labels.add(new LabelEntity(
                    resultSet.getLong("label_id"),
                    resultSet.getString("name"))
            );
        }
        return labels;
    }

    @Override
    public void attachLabelsToPost(List<LabelEntity> labels, Long postId) throws SQLException {
        for(LabelEntity i : labels){
            PreparedStatement statement1 = connection.prepareStatement(ATTACH_LABELS_TO_POST);
            statement1.setLong(1, postId);
            statement1.setLong(2, i.getId());
            statement1.execute();
        }
    }

    @Override
    public void updateLabelsByPost(List<LabelEntity> labels, Long postId) throws SQLException {
        PreparedStatement statement;

        //Удаляю убранные тэги:
        if(labels.iterator().hasNext()) {
            //Если список тэгов не пустой:
            //удаляю снятые с поста тэги. Для этого:
            //задаю кол-во параметров в соответствии с кол-вом объектов Label
            String detachLabelsById = "DELETE FROM labeled_post WHERE post_id = ? AND label_id NOT IN (";
            StringJoiner joiner = new StringJoiner(",");
            for(int i = 0; i < labels.size(); i++) {
                joiner.add("?");
                if (i == labels.size()-1) detachLabelsById = detachLabelsById + joiner + ")";
            }

            //присваиваю параметрам конкретные значения
            statement = connection.prepareStatement(detachLabelsById);
            int j = 2;
            statement.setLong(1, postId);
            for(LabelEntity i : labels) {
                statement.setLong(j, i.getId());
                j++;
            }
            statement.execute();

            //Если есть новые тэги, то добавляю в таблицу labeled_post:
            attachLabelsToPost(labels, postId);
        }
        else {
            //Если список тэгов пустой
            statement = connection.prepareStatement(DETACH_ALL_LABELS);
            statement.setLong(1, postId);
            statement.execute();
        }
    }
}

