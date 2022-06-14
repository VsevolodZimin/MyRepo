package service.impl;

import domain.WriterEntity;
import repository.WriterRepository;
import repository.impl.JDBCWriterRepositoryImpl;
import service.PostService;
import service.WriterService;

import java.sql.SQLException;
import java.util.List;

public class WriterServiceImpl implements WriterService {

    private WriterRepository repository;
    private PostService pService;


    public WriterServiceImpl(WriterRepository repository, PostService pService) throws SQLException {
        this.repository = repository;
        this.pService = pService;
    }


    @Override
    public List<WriterEntity> findAll() throws SQLException {
        List<WriterEntity> writers = repository.findAll();
        for (WriterEntity i : writers) {
            i.setPosts(pService.findAssociatedPosts(i));
        }
        return writers;
    }


    @Override
    public WriterEntity findById (Long id) throws SQLException {
        WriterEntity writer = repository.findById(id);
        writer.setPosts(pService.findAssociatedPosts(writer));
        return writer;
    }


    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        return repository.save(writer);
    }


    @Override
    public WriterEntity update(WriterEntity writer) throws SQLException {
        return repository.update(writer);
    }


    @Override
    public void delete(WriterEntity writer) throws SQLException {
        repository.deleteById(writer.getId());
    }
}
