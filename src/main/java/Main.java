

import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import domain.LabelEntity;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import repository.impl.JDBCLabelRepositoryImpl;
import repository.impl.JDBCPostRepositoryImpl;
import repository.impl.JDBCWriterRepositoryImpl;
import service.LabelService;
import service.PostService;
import service.WriterService;
import service.impl.LabelServiceImpl;
import service.impl.PostServiceImpl;
import service.impl.WriterServiceImpl;
import utils.connectionPool.DataSource;
import utils.connectionPool.impl.JDBCConnectionPool;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        JDBCConnectionPool.initialise(new DataSource());
        LabelRepository lRepo = new JDBCLabelRepositoryImpl();
        PostRepository pRepo = new JDBCPostRepositoryImpl();
        WriterRepository wRepo = new JDBCWriterRepositoryImpl();

        LabelService lService = new LabelServiceImpl(lRepo);
        PostService pService = new PostServiceImpl(pRepo, lService);
        WriterService wService = new WriterServiceImpl(wRepo, pService);
        pService.setWriterService(wService);

        WriterController wController = new WriterController(wService);
        PostController pController = new PostController(pService);
        LabelController lController = new LabelController(lService);

        List<LabelEntity> labels = lController.findAll();

        for(LabelEntity i: labels){
            System.out.println(i.getName());
        }
    }
}