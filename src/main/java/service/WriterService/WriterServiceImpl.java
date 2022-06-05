package service.WriterService;

import domain.WriterEntity;
import repository.WriterRepository.WriterRepository;
import repository.WriterRepository.impl.JDBCWriterRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class WriterServiceImpl implements WriterService {

    WriterRepository repository = new JDBCWriterRepositoryImpl();

    public WriterServiceImpl() throws SQLException {
    }

    @Override
    public List<WriterEntity> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public WriterEntity findById(Long id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public WriterEntity save(WriterEntity writer) throws SQLException {
        return repository.save(writer);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        repository.deleteById(id);

    }

    @Override
    public WriterEntity update(WriterEntity writer, Long id) throws SQLException {
        return repository.update(writer, id);
    }
}
