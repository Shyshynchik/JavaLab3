package mysql.connection;

import java.sql.*;

public class MySQLConnection {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/device";
    private static final String user = "root";
    private static final String password = "root";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }

}
