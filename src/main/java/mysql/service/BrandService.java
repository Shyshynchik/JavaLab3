package mysql.service;

import mysql.connection.MySQLConnection;
import mysql.dao.DAOAccessories;
import mysql.entity.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandService extends MySQLConnection implements DAOAccessories<Brand> {

    private final Connection connection;

    public BrandService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Brand getById(Integer id) {
        PreparedStatement statement = null;

        String sql = "SELECT * FROM brand WHERE id=?";

        Brand brand = new Brand();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                brand.setId(resultSet.getInt("id"));
                brand.setName(resultSet.getString("name"));
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

        return brand;
    }

    @Override
    public Brand getByNameOrAdd(String name) {
        PreparedStatement statement = null;

        String sql = "SELECT * FROM brand WHERE name=?";

        Brand brand = new Brand();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                brand.setId(resultSet.getInt("id"));
                brand.setName(resultSet.getString("name"));
            } else {
                brand = add(name);
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

        return brand;
    }

    @Override
    public List<Brand> getAll() {
        ArrayList<Brand> osArrayList = new ArrayList<>();
        PreparedStatement statement = null;

        String sql = "SELECT * FROM brand";

        try {
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Brand brand = new Brand();
                brand.setId(resultSet.getInt("id"));
                brand.setName(resultSet.getString("name"));

                osArrayList.add(brand);
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

    public Brand add(String name) {
        Brand brand = new Brand();

        PreparedStatement statement = null;
        String sql = "INSERT INTO brand (name) " + "VALUES (?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            brand = getById(resultSet.getInt(1));

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

        return brand;
    }
}
