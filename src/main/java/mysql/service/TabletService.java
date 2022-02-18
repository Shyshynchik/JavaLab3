package mysql.service;

import device.impl.TabletImpl;
import mysql.connection.MySQLConnection;
import mysql.dao.DAOTablet;
import mysql.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TabletService extends MySQLConnection implements DAOTablet<Tablet> {

    private final Connection connection;
    private final OsService osService;
    private final BrandService brandService;

    public TabletService(Connection connection) {
        this.connection = connection;
        osService = new OsService(connection);
        brandService = new BrandService(connection);
    }

    @Override
    public Tablet getById(Integer id) {
        PreparedStatement statement = null;

        String sql = "select * from tablet where id=?";

        Tablet tablet = new Tablet();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tablet = setTablet(resultSet);
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

        return tablet;
    }

    @Override
    public List<Tablet> getAll() {
        ArrayList<Tablet> tabletArrayList = new ArrayList<>();
        Statement statement = null;

        String sql = "select * from tablet";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Tablet tablet = setTablet(resultSet);

                tabletArrayList.add(tablet);
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

        return tabletArrayList;
    }

    @Override
    public Tablet add(String brand, Double diagonal, Integer ram, String os, Integer memory) {
        Tablet tablet = new Tablet();

        PreparedStatement statement = null;
        String sql = "INSERT INTO tablet (brand, diagonal, ram, os, memory) " + "VALUES (?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Brand brandAdd = brandService.getByNameOrAdd(brand);
            Os osAdd = osService.getByNameOrAdd(os);

            statement.setInt(1, brandAdd.getId());
            statement.setDouble(2,diagonal);
            statement.setInt(3,ram);
            statement.setInt(4,osAdd.getId());
            statement.setInt(5,memory);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            tablet = getById(resultSet.getInt(1));

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

        return tablet;
    }

    @Override
    public Tablet add(TabletImpl tabletImpl) {
        Tablet tablet = new Tablet();

        PreparedStatement statement = null;
        String sql = "INSERT INTO tablet (brand, diagonal, ram, os, memory) " + "VALUES (?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Brand brandAdd = brandService.getByNameOrAdd(tabletImpl.getBrand());
            Os osAdd = osService.getByNameOrAdd(tabletImpl.getOs());

            statement.setInt(1, brandAdd.getId());
            statement.setString(2,tabletImpl.getDiagonal());
            statement.setString(3,tabletImpl.getRam());
            statement.setInt(4,osAdd.getId());
            statement.setString(5,tabletImpl.getMemory());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            tablet = getById(resultSet.getInt(1));

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

        return tablet;
    }

    @Override
    public List<Tablet> find(String brand, String os) {
        ArrayList<Tablet> tabletArrayList = new ArrayList<>();

        PreparedStatement statement = null;
        String sql = "SELECT * FROM tablet";

        try {
            if (brand.equals("-") && os.equals("-")) {
                return getAll();
            }

            sql = makeSql(sql, brand, os);

            statement = connection.prepareStatement(sql);

            int i = 1;
            if (!brand.equals("-")) {
                statement.setInt(i, brandService.getByNameOrAdd(brand).getId());
                i++;
            }
            if (!os.equals("-")) {
                statement.setInt(i, osService.getByNameOrAdd(os).getId());
            }

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Tablet tablet = setTablet(resultSet);

                tabletArrayList.add(tablet);
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

        return tabletArrayList;
    }

    private Tablet setTablet(ResultSet resultSet) {
        Tablet tablet = new Tablet();

        try {
            Brand brand = brandService.getById(resultSet.getInt("brand"));
            Os os = osService.getById(resultSet.getInt("os"));


            tablet.setId(resultSet.getInt("id"));
            tablet.setBrand(brand);
            tablet.setDiagonal(resultSet.getInt("diagonal"));
            tablet.setRam(resultSet.getInt("ram"));
            tablet.setMemory(resultSet.getInt("memory"));
            tablet.setOs(os);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return tablet;
    }

    private String makeSql(String sql, String brand, String os) {
        StringBuilder stringBuilderSql = new StringBuilder(sql);
        stringBuilderSql.append(" WHERE ");
        int i = 1;

        if (!brand.equals("-")) {
            stringBuilderSql.append("brand = ?");
            i++;
        }

        if (!os.equals("-")) {
            if (i > 1) {
                stringBuilderSql.append(" AND ");
            }
            stringBuilderSql.append("os = ?");
        }

        return stringBuilderSql.toString();
    }

}
