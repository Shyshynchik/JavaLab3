package mysql.service;

import java.sql.*;

public class DBService {

    private final Connection connection;

    public DBService(Connection connection) {
        this.connection = connection;
    }

    public void deleteData() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.addBatch("DELETE FROM brand");
            statement.addBatch("DELETE FROM cpu");
            statement.addBatch("DELETE FROM os");
            statement.addBatch("DELETE FROM video_card");
            statement.addBatch("ALTER TABLE brand AUTO_INCREMENT = 1");
            statement.addBatch("ALTER TABLE cpu AUTO_INCREMENT = 1");
            statement.addBatch("ALTER TABLE laptop AUTO_INCREMENT = 1");
            statement.addBatch("ALTER TABLE os AUTO_INCREMENT = 1");
            statement.addBatch("ALTER TABLE tablet AUTO_INCREMENT = 1");
            statement.addBatch("ALTER TABLE video_card AUTO_INCREMENT = 1");
            statement.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
