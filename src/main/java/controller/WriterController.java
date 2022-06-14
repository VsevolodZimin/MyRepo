package controller;

import domain.WriterEntity;
import service.WriterService;
import service.impl.WriterServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class WriterController {

    WriterService service;

    public WriterController (WriterService service){
        this.service = service;
    }


    public List<WriterEntity> findAll() throws SQLException {
        return service.findAll();
    }

    public WriterEntity findById(Long id) throws SQLException {
        return service.findById(id);
    }

    public void save(WriterEntity writer) throws SQLException {
        service.save(writer);

    }

    public void delete(WriterEntity writer) throws SQLException {
        service.delete(writer);

    }

    public void update(WriterEntity writer) throws SQLException {
        service.update(writer);
    }
}
