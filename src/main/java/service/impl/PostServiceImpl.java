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

        for(PostEntity post : posts){
            post.setLabels(lService.findAssociatedLabels(post));
        }
    return posts;
    }


    @Override
    public PostEntity findById(Long id) throws SQLException {
        PostEntity post = repository.findById(id);
        post.setLabels(lService.findAssociatedLabels(post));
    return post;
    }


    public PostEntity save(PostEntity newPost) throws SQLException {
        repository.save(newPost);
        lService.attachNewLabelsToPost(newPost);
        return newPost;
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
        List<PostEntity> posts = repository.findAssociatedPostsByWriterId(writer.getId());
        for (PostEntity i: posts){
            i.setLabels(lService.findAssociatedLabels(i));
        }
    return posts;
    }

    @Override
    public void setWService(WriterService wService) {
        this.wService = wService;
    }
}
