package repository;

import domain.LabelEntity;

import java.sql.SQLException;
import java.util.List;

public interface  GenericRepository<E,ID> {

    List<E> findAll() throws SQLException;
    E findById(ID id) throws SQLException;
    E save(E e) throws SQLException;
    E update(E e) throws SQLException;
    void deleteById(ID id) throws SQLException;
}

