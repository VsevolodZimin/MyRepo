package controller;

import domain.PostEntity;
import service.PostService;
import service.impl.PostServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class PostController {

    PostService service;

    public PostController(PostService service) throws SQLException {
        this.service = service;
    }

    public List<PostEntity> findAll() throws SQLException {
        return service.findAll();
    }

    public PostEntity findById(Long id) throws SQLException {
        return service.findById(id);
    }

    public PostEntity save(PostEntity postEntity) throws SQLException{
        return service.save(postEntity);
    }

    public PostEntity update(PostEntity post) throws SQLException {
        return service.update(post);
    }

    public void delete(PostEntity post) throws SQLException {
        service.delete(post);
    }
}