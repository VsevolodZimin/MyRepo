package controller;

import domain.PostEntity;
import service.PostService.PostService;
import service.PostService.PostServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class PostController {

    PostService postService = new PostServiceImpl();

    public PostController() throws SQLException {
    }

    public List<PostEntity> findAll() throws SQLException {
        return postService.findAll();
    }

    public PostEntity findById(Long id) throws SQLException {
        return postService.findById(id);
    }

    public PostEntity save(PostEntity postEntity, Long writerId) throws SQLException{
        return postService.save(postEntity, writerId);
    }

    public PostEntity save(PostEntity post) throws SQLException {
        return postService.save(post);
    }

    public PostEntity update(PostEntity post, Long id) throws SQLException {
        return postService.update(post, id);
    }

    public void deleteById(Long id) throws SQLException {
        postService.deleteById(id);
    }
}