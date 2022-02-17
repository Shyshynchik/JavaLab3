package mysql.service;

import mysql.connection.MySQLConnection;
import mysql.dao.DAOAccessories;
import mysql.entity.Os;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OsService extends MySQLConnection implements DAOAccessories<Os> {

    private final Connection connection;

    public OsService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Os getById(Integer id) {
        PreparedStatement statement = null;

        String sql = "select * from os where id=?";

        Os os = new Os();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                os.setId(resultSet.getInt("id"));
                os.setName(resultSet.getString("name"));
            }

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

        return os;
    }

    @Override
    public Os getByNameOrAdd(String name) {
        PreparedStatement statement = null;

        String sql = "select * from os where name=?";

        Os os = new Os();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                os.setId(resultSet.getInt("id"));
                os.setName(resultSet.getString("name"));
            } else {
                os = add(name);
            }



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

        return os;
    }

    @Override
    public List<Os> getAll() {
        ArrayList<Os> osArrayList = new ArrayList<>();
        PreparedStatement statement = null;

        String sql = "select * from os";

        try {
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Os os = new Os();
                os.setId(resultSet.getInt("id"));
                os.setName(resultSet.getString("name"));

                osArrayList.add(os);
            }

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

        return osArrayList;
    }

    @Override
    public Os add(String name) {
        Os os = new Os();

        PreparedStatement statement = null;
        String sql = "INSERT INTO os (name) " + "VALUES (?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            os = getById(resultSet.getInt(1));

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

        return os;
    }
}
