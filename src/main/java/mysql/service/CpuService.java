package mysql.service;

import mysql.connection.MySQLConnection;
import mysql.dao.DAOAccessories;
import mysql.entity.Cpu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CpuService extends MySQLConnection implements DAOAccessories<Cpu> {

    private final Connection connection;

    public CpuService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Cpu getById(Integer id) {
        PreparedStatement statement = null;

        String sql = "select * from cpu where id=?";

        Cpu cpu = new Cpu();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cpu.setId(resultSet.getInt("id"));
                cpu.setName(resultSet.getString("name"));
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

        return cpu;
    }

    @Override
    public Cpu getByNameOrAdd(String name) {
        PreparedStatement statement = null;

        String sql = "select * from cpu where name=?";

        Cpu cpu = new Cpu();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cpu.setId(resultSet.getInt("id"));
                cpu.setName(resultSet.getString("name"));
            } else {
                cpu = add(name);
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

        return cpu;
    }

    @Override
    public List<Cpu> getAll() {
        ArrayList<Cpu> osArrayList = new ArrayList<>();
        PreparedStatement statement = null;

        String sql = "select * from cpu";

        try {
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Cpu cpu = new Cpu();
                cpu.setId(resultSet.getInt("id"));
                cpu.setName(resultSet.getString("name"));

                osArrayList.add(cpu);
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
    public Cpu add(String name) {
        Cpu cpu = new Cpu();

        PreparedStatement statement = null;
        String sql = "INSERT INTO cpu (name) " + "VALUES (?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            cpu = getById(resultSet.getInt(1));

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

        return cpu;
    }
}
