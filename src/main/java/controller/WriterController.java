package controller;

import domain.WriterEntity;
import service.WriterService.WriterService;
import service.WriterService.WriterServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class WriterController {

    WriterService service = new WriterServiceImpl();

    public WriterController() throws SQLException {
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

    public void deleteById(Long id) throws SQLException {
        service.deleteById(id);

    }

    public void update(WriterEntity writer, Long id) throws SQLException {
        service.update(writer, id);
    }
}
