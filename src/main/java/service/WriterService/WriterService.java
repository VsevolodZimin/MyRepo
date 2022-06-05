package service.WriterService;

import domain.PostEntity;
import domain.WriterEntity;

import java.sql.SQLException;
import java.util.List;


public interface WriterService {
    List<WriterEntity> findAll() throws SQLException;
    WriterEntity findById(Long id) throws SQLException;
    WriterEntity save(WriterEntity writer) throws SQLException;
    WriterEntity update (WriterEntity writer, Long id) throws SQLException;
    void deleteById (Long id) throws SQLException;
}