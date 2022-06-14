package service;

import domain.LabelEntity;
import domain.PostEntity;

import java.sql.SQLException;
import java.util.List;

public interface  LabelService {

    List<LabelEntity> findAll() throws SQLException;

    LabelEntity findById(Long id) throws SQLException;

    LabelEntity save(LabelEntity e) throws SQLException;

    LabelEntity update(LabelEntity e) throws SQLException;

    void delete(LabelEntity label) throws SQLException;

    List<LabelEntity> findAssociatedLabels(PostEntity post) throws SQLException;

    void attachNewLabelsToPost(PostEntity post) throws SQLException;

    void updatePostLabels(PostEntity post) throws SQLException;
}