package service.PostService;

import domain.PostEntity;
import repository.PostRepository.PostRepository;
import repository.PostRepository.impl.JDBCPostRepositoryImpl;
import service.LabelService.LabelService;
import service.LabelService.impl.LabelServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class PostServiceImpl implements PostService {

    PostRepository repository = new JDBCPostRepositoryImpl();

    public PostServiceImpl() throws SQLException {
    }

    @Override
    public List<PostEntity> findAll() throws SQLException {
        return repository.findAll();
    }


    @Override
    public PostEntity findById(Long id) throws SQLException {
        return repository.findById(id);
    }


    public PostEntity save(PostEntity postEntity, Long writerId) throws SQLException {
        return repository.save(postEntity, writerId);
    }

    @Override
    public PostEntity save(PostEntity post) throws SQLException {
        return repository.save(post);
    }


    @Override
    public PostEntity update(PostEntity post, Long id) throws SQLException {
        return repository.update(post, id);
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        repository.deleteById(id);
    }

    @Override
    public List<PostEntity> findAssociatedPosts(Long id) throws SQLException {
        return repository.findAssociatedPosts(id);
    }

    @Override
    public void attachPostToWriter(PostEntity post, Long id) throws SQLException {
        repository.attachPostToWriter(post, id);
    }
}
