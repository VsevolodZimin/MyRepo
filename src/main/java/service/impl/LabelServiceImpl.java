package service.impl;

import domain.LabelEntity;
import domain.PostEntity;
import repository.LabelRepository;
import service.LabelService;

import java.sql.SQLException;
import java.util.List;

public class LabelServiceImpl implements LabelService {


    private final LabelRepository repository;


    public LabelServiceImpl(LabelRepository repository){
        this.repository = repository;
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
    public LabelEntity update(LabelEntity e) throws SQLException {
        return repository.update(e);
    }

    @Override
    public void delete(LabelEntity label) throws SQLException {
        repository.deleteById(label.getId());
    }

    @Override
    public List<LabelEntity> findAssociatedLabels(PostEntity post) throws SQLException {
        return repository.findAssociatedLabels(post.getId());
    }

    @Override
    public void attachNewLabelsToPost(PostEntity post) throws SQLException {
        repository.attachNewLabelsToPost(post);
    }

    @Override
    public void updatePostLabels(PostEntity post) throws SQLException {
        if(post.getLabels().iterator().hasNext()) { //если список тэгов не пустой...
            repository.detachLabelsById(post.getLabels(), post.getId()); //удаляет из сводной таблицы тэги, отсутствующие в списке labels.
            attachNewLabelsToPost(post); //добавляет новые тэги в БД (только если они новые; иначе игнорирует добавление)
        }
        else { //если список тэгов пустой...
            repository.detachAllLabels(post.getId()); //из сводной таблицы удаляются все тэги, связанные с данным постом
        }
    }
}
