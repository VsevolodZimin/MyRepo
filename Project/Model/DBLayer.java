package Model;

import java.sql.*;

public class DBLayer implements ModelLayer{

    @Override
    public Label getLabel(Connection connection, Label label) throws SQLException {
        Label resultLabel = new Label();
        String sql = "SELECT * FROM label WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, label.id);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            resultLabel.id = result.getInt(1);
            resultLabel.name = result.getString(2);
        }
        return resultLabel;
    }
    @Override
    public int putLabel(Connection connection, Label newLabel) throws SQLException {
        String sql = "INSERT INTO label (name) VALUES (?)";
        PreparedStatement statement2 = connection.prepareStatement(sql);
        statement2.setString(1, newLabel.name);
        int rowsAffected = statement2.executeUpdate();
        return rowsAffected;
    }

    @Override
    public int updateLabel(
            Connection connection,
            Label label
    ) throws SQLException {
        String sql ="UPDATE label SET name = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, label.name);
        statement.setInt(2, label.id);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected;
    }

    @Override
    public int deleteLabel(Connection connection, Label label) throws SQLException {
        String sql = "DELETE FROM label WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, label.id);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected;
    }

    @Override
    public ResultSet searchLabelByName(Connection connection, Label label) throws SQLException {
        String sql = "SELECT DISTINCT * FROM label WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, label.name);
        ResultSet result = statement.executeQuery();
        return result;
    }

    @Override
    public ResultSet searchLabelById(Connection connection, Label label) throws SQLException {
        String sql = "SELECT DISTINCT name FROM label WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, label.id);
        ResultSet result = statement.executeQuery();
        return result;
    }
}