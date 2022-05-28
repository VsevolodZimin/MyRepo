package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public interface ModelLayer {

        Label getLabel(Connection connection, Label label) throws SQLException;

        int putLabel(Connection connection, Label label) throws SQLException;

        int updateLabel(Connection connection, Label label) throws SQLException;

        int deleteLabel(Connection connection, Label label) throws SQLException;

        ResultSet searchLabelById(Connection connection, Label label) throws SQLException;

        ResultSet searchLabelByName(Connection connection, Label label) throws SQLException;
    }