package repository.PostRepository;

import domain.PostEntity;
import domain.WriterEntity;
import repository.GenericRepository;

import java.sql.SQLException;
import java.util.List;

public interface PostRepository extends GenericRepository<PostEntity, Long>{

    List<PostEntity> findAssociatedPosts(Long id) throws SQLException;
}