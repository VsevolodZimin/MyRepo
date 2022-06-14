package repository;

import domain.LabelEntity;
import domain.PostEntity;

import java.awt.event.PaintEvent;
import java.sql.SQLException;
import java.util.List;

public interface LabelRepository extends GenericRepository<LabelEntity, Long> {
    List<LabelEntity> findAssociatedLabels(Long id) throws SQLException;
    List<LabelEntity> attachNewLabelsToPost(PostEntity post) throws SQLException;
    void detachLabelsById(List<LabelEntity> labels, Long postId) throws SQLException;
    void detachAllLabels(Long postId) throws SQLException;
}

