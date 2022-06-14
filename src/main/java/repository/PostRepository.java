package repository;

import domain.PostEntity;
import domain.WriterEntity;
import repository.GenericRepository;

import java.sql.SQLException;
import java.util.List;

public interface PostRepository extends GenericRepository<PostEntity, Long>{

    List<PostEntity> findAssociatedPostsByWriterId(Long writerId) throws SQLException;
}