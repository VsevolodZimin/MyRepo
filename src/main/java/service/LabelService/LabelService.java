package service.LabelService;

import domain.LabelEntity;
import utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface  LabelService {

    List<LabelEntity> findAll() throws SQLException;

    LabelEntity findById(Long id) throws SQLException;

    LabelEntity save(LabelEntity e) throws SQLException;

    LabelEntity update(LabelEntity e, Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;

    List<LabelEntity> findAssociatedLabels(Long id) throws SQLException;

    void attachLabelsToPost(List<LabelEntity> labels, Long postId) throws SQLException;

    void updateLabelsByPost(List<LabelEntity> labels, Long postId) throws SQLException;
}
