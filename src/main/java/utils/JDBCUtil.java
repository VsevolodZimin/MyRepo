package utils;

import domain.LabelEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class JDBCUtil {

    public static String sqlQueryToDetachLabels(List<LabelEntity> labels){
    String detachLabelsById = "DELETE FROM post_label WHERE post_id = ? AND label_id NOT IN (";
    StringJoiner joiner = new StringJoiner(",");
        for(int i = 1; i <= labels.size(); i++) {
            joiner.add("?");
            if (i == labels.size()) detachLabelsById = detachLabelsById + joiner + ")";
        }
    return detachLabelsById;
    }
}
