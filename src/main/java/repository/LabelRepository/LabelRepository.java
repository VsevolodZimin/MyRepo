package repository.LabelRepository;

import domain.LabelEntity;
import repository.GenericRepository;

import java.sql.SQLException;
import java.util.List;

public interface LabelRepository extends GenericRepository<LabelEntity, Long> {
    List<LabelEntity> findAssociatedLabels(Long id) throws SQLException;
    void attachLabelsToPost(List<LabelEntity> labels, Long postId) throws SQLException;
    void updateLabelsByPost(List<LabelEntity> labels, Long postId) throws SQLException;
}

