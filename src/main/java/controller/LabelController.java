package controller;

import domain.LabelEntity;
import service.LabelService.LabelService;
import service.LabelService.impl.LabelServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class LabelController {

    private final LabelService service = new LabelServiceImpl();

    public LabelController() throws SQLException {
    }

    public List<LabelEntity> findAll() throws SQLException {
        return service.findAll();
    }

    public LabelEntity findById(Long id) throws SQLException {
        return service.findById(id);
    }

    public LabelEntity save(LabelEntity label) throws SQLException {
        return service.save(label);
    }

    public LabelEntity update(LabelEntity e, Long id) throws SQLException {
        return service.update(e, id);
    }

    public void deleteById(Long id) throws SQLException {
        service.deleteById(id);
    }

    List<LabelEntity> findAssociatedLabels(Long id) throws SQLException{
       return service.findAssociatedLabels(id);
    }

    void attachLabelsToPost(List<LabelEntity> labels, Long postId) throws SQLException{
        service.attachLabelsToPost(labels, postId);
    }

    void updateLabelsByPost(List<LabelEntity> labels, Long postId) throws SQLException{
        service.updateLabelsByPost(labels, postId);
    }
}