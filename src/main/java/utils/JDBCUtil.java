package utils;

import domain.LabelEntity;
import service.LabelService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class JDBCUtil {

    public static final String URL = "jdbc:mysql://localhost:3306/testdb";
    public static final String USER = "root";
    public static final String PASSWORD = "Resufe05";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String detachLabelString(List<LabelEntity> labels){
    String detachLabelsById = "DELETE FROM post_label WHERE post_id = ? AND label_id NOT IN (";
    StringJoiner joiner = new StringJoiner(",");
        for(int i = 0; i < labels.size(); i++) {
            joiner.add("?");
            if (i == labels.size() - 1) detachLabelsById = detachLabelsById + joiner + ")";
        }
    return detachLabelsById;
    }
}
