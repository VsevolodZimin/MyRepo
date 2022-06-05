package service.LabelService.impl;

import domain.LabelEntity;
import repository.LabelRepository.LabelRepository;
import repository.LabelRepository.impl.JDBCLabelRepositoryImpl;
import service.LabelService.LabelService;

import java.sql.SQLException;
import java.util.List;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository = new JDBCLabelRepositoryImpl();

    public LabelServiceImpl() throws SQLException {
    }

    @Override
    public List<LabelEntity> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public LabelEntity findById(Long id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public LabelEntity save(LabelEntity e) throws SQLException {
        return repository.save(e);
    }

    @Override
    public LabelEntity update(LabelEntity e, Long id) throws SQLException {
        return repository.update(e, id);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        repository.deleteById(id);
    }

    @Override
    public List<LabelEntity> findAssociatedLabels(Long id) throws SQLException {
        return repository.findAssociatedLabels(id);
    }

    @Override
    public void attachLabelsToPost(List<LabelEntity> labels, Long postId) throws SQLException {
         repository.attachLabelsToPost(labels, postId);
    }

    @Override
    public void updateLabelsByPost(List<LabelEntity> labels, Long postId) throws SQLException {
        repository.updateLabelsByPost(labels, postId);
    }
}
