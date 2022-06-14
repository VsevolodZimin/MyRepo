package service.impl;

import domain.PostEntity;
import domain.WriterEntity;
import repository.PostRepository;
import service.LabelService;
import service.PostService;
import service.WriterService;

import java.sql.SQLException;
import java.util.List;

public class PostServiceImpl implements PostService {

    // Никаких созданных объектов через new на уровне класса
    // Объекты инжектятся через конструктор
    private PostRepository repository;
    private WriterService wService;
    private LabelService lService;


    public PostServiceImpl (PostRepository repository, LabelService lService) {
        this.repository = repository;
        this.lService = lService;
    }

    @Override
    public List<PostEntity> findAll() throws SQLException {
        List<PostEntity> posts = repository.findAll();

        for(PostEntity i : posts){
            Long writerId = i.getWriterId();
            i.setLabels(lService.findAssociatedLabels(i));
            i.setWriterEntity(wService.findById(writerId));
        }
    return posts;
    }


    @Override
    public PostEntity findById(Long id) throws SQLException {
        PostEntity post = repository.findById(id);
        Long writerId = post.getWriterId();
        post.setLabels(lService.findAssociatedLabels(post));
        post.setWriterEntity(wService.findById(writerId));
    return post;
    }


    public PostEntity save(PostEntity updatedPost) throws SQLException {
        repository.save(updatedPost);
        lService.attachNewLabelsToPost(updatedPost);
        return updatedPost;
    }


    @Override
    public PostEntity update(PostEntity post) throws SQLException {
        repository.update(post);
        lService.updatePostLabels(post);
        return post;
    }


    @Override
    public void delete(PostEntity post) throws SQLException {
        repository.deleteById(post.getId());
    }


    @Override
    public List<PostEntity> findAssociatedPosts(WriterEntity writer) throws SQLException {
        return repository.findAssociatedPostsByWriterId(writer.getId());
    }


    public void setWService(WriterService wService) {
        this.wService = wService;
    }
}
