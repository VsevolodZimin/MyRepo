package service;
import domain.PostEntity;
import domain.WriterEntity;

import java.sql.SQLException;
import java.util.List;

public interface PostService{
    List<PostEntity> findAll() throws SQLException;

    PostEntity findById(Long id) throws SQLException;

    PostEntity save(PostEntity post) throws SQLException;

    PostEntity update(PostEntity post) throws SQLException;

    void delete(PostEntity post) throws SQLException;

    List<PostEntity> findAssociatedPosts(WriterEntity writer) throws SQLException;
}
