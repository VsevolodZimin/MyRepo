package service.PostService;
import domain.LabelEntity;
import domain.PostEntity;
import service.LabelService.LabelService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PostService{
    List<PostEntity> findAll() throws SQLException;

    PostEntity findById(Long id) throws SQLException;

    PostEntity save(PostEntity post) throws SQLException;

    PostEntity save(PostEntity postEntity, Long writerId) throws SQLException;

    PostEntity update(PostEntity post, Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;

    List<PostEntity> findAssociatedPosts(Long id) throws SQLException;

    void attachPostToWriter(PostEntity post, Long id) throws SQLException;
}
