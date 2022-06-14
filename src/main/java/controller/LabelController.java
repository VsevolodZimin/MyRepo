package controller;

import domain.LabelEntity;
import service.LabelService;
import service.impl.LabelServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class LabelController {

    private final LabelService service;

    public LabelController(LabelService service) {
        this.service = service;
    }

    public List<LabelEntity> findAll() throws SQLException {
        return service.findAll();
    }

    public LabelEntity findById(Long id) throws SQLException {
        return service.findById(id);
    }

    public LabelEntity save(LabelEntity label) throws SQLException {
        return service.save(label);
    }

    public LabelEntity update(LabelEntity label) throws SQLException {
        return service.update(label);
    }

    public void delete(LabelEntity label) throws SQLException {
        service.delete(label);
    }
}