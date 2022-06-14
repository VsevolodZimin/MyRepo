package service;

import domain.PostEntity;
import domain.WriterEntity;

import java.sql.SQLException;
import java.util.List;


public interface WriterService {
    List<WriterEntity> findAll() throws SQLException;
    WriterEntity findById(Long id) throws SQLException;
    WriterEntity save(WriterEntity writer) throws SQLException;
    WriterEntity update (WriterEntity writer) throws SQLException;
    void delete (WriterEntity writer) throws SQLException;
}