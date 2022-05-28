package Controller;

import Service.LabelService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * LabelController отвечает за передачу задачи ответственному за её выполнение сервису (в данном случае, сервис всего один)
 */

public class LabelController {
    private Connection connection = null;
    private LabelService service = new LabelService(connection);

    public LabelService getService() {
        return service;
    }

    //открываю соединение с БД и передаю объект соединения сервису
    public void connect(){
        String url;
        String username;
        String password;
        try {
            url = "jdbc:mysql://localhost:3306/testdb";
            username = "root";
            password = "Resufe05";
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                service.connection = connection;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //закрываю соединение
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}