package mysql.service;

import device.impl.LaptopImpl;
import mysql.connection.MySQLConnection;
import mysql.dao.DAOLaptop;
import mysql.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaptopService extends MySQLConnection implements DAOLaptop<Laptop> {

    private final Connection connection;
    private final BrandService brandService;
    private final CpuService cpuService;
    private final VideoCardService videoCardService;

    public LaptopService(Connection connection) {
        this.connection = connection;
        brandService = new BrandService(connection);
        cpuService = new CpuService(connection);
        videoCardService = new VideoCardService(connection);
    }

    @Override
    public Laptop getById(Integer id) {

        PreparedStatement statement = null;

        String sql = "select * from laptop where id=?";

        Laptop laptop = new Laptop();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                laptop = setLaptop(resultSet);
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

        return laptop;
    }

    @Override
    public List<Laptop> getAll() {
        ArrayList<Laptop> laptopArrayList = new ArrayList<>();
        Statement statement = null;

        String sql = "select * from laptop";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Laptop laptop = setLaptop(resultSet);

                laptopArrayList.add(laptop);
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

        return laptopArrayList;
    }

    @Override
    public Laptop add(String brand, Double diagonal, Integer ram, String cpu, String videoCard) {
        Laptop laptop = new Laptop();

        PreparedStatement statement = null;
        String sql = "INSERT INTO laptop (brand, diagonal, ram, cpu, video_card) " + "VALUES (?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Brand brandAdd = brandService.getByNameOrAdd(brand);
            Cpu cpuAdd = cpuService.getByNameOrAdd(cpu);
            VideoCard videoCardAdd = videoCardService.getByNameOrAdd(videoCard);

            statement.setInt(1, brandAdd.getId());
            statement.setDouble(2,diagonal);
            statement.setInt(3,ram);
            statement.setInt(4,cpuAdd.getId());
            statement.setInt(5,videoCardAdd.getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            laptop = getById(resultSet.getInt(1));

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

        return laptop;
    }

    @Override
    public Laptop add(LaptopImpl laptopImpl) {
        Laptop laptop = new Laptop();

        PreparedStatement statement = null;
        String sql = "INSERT INTO laptop (brand, diagonal, ram, cpu, video_card) " + "VALUES (?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Brand brand = brandService.getByNameOrAdd(laptopImpl.getBrand());
            Cpu cpu = cpuService.getByNameOrAdd(laptopImpl.getCpu());
            VideoCard videoCard = videoCardService.getByNameOrAdd(laptopImpl.getVideoCard());

            statement.setInt(1, brand.getId());
            statement.setString(2,laptopImpl.getDiagonal());
            statement.setString(3,laptopImpl.getRam());
            statement.setInt(4,cpu.getId());
            statement.setInt(5,videoCard.getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            laptop = getById(resultSet.getInt(1));

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

        return laptop;
    }

    @Override
    public List<Laptop> find(String brand, String cpu, String videoCard) {
        ArrayList<Laptop> laptopArrayList = new ArrayList<>();

        PreparedStatement statement = null;
        String sql = "SELECT * FROM laptop";

        try {
            if (cpu.equals("-") && brand.equals("-") && videoCard.equals("-")) {
                return getAll();
            }

            sql = makeSql(sql, brand, cpu, videoCard);

            statement = connection.prepareStatement(sql);

            int i = 1;
            if (!brand.equals("-")) {
                statement.setInt(i, brandService.getByNameOrAdd(brand).getId());
                i++;
            }
            if (!cpu.equals("-")) {
                statement.setInt(i, cpuService.getByNameOrAdd(cpu).getId());
                i++;
            }
            if (!videoCard.equals("-")) {
                statement.setInt(i, videoCardService.getByNameOrAdd(videoCard).getId());
            }

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Laptop laptop = setLaptop(resultSet);

                laptopArrayList.add(laptop);
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

        return laptopArrayList;
    }

    private Laptop setLaptop(ResultSet resultSet) {
        Laptop laptop = new Laptop();

        try {
            Brand brand = brandService.getById(resultSet.getInt("brand"));
            Cpu cpu = cpuService.getById(resultSet.getInt("cpu"));
            VideoCard videoCard = videoCardService.getById(resultSet.getInt("video_card"));


            laptop.setId(resultSet.getInt("id"));
            laptop.setBrand(brand);
            laptop.setDiagonal(resultSet.getInt("diagonal"));
            laptop.setRam(resultSet.getInt("ram"));
            laptop.setCpu(cpu);
            laptop.setVideoCard(videoCard);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return laptop;
    }

    private String makeSql(String sql, String brand, String cpu, String videoCard) {
        StringBuilder stringBuilderSql = new StringBuilder(sql);
        stringBuilderSql.append(" WHERE ");
        int i = 1;

        if (!brand.equals("-")) {
            stringBuilderSql.append("brand = ?");
            i++;
        }

        if (!cpu.equals("-")) {
            if (i > 1) {
                stringBuilderSql.append(" AND ");
            }
            stringBuilderSql.append("cpu = ?");
            i++;
        }

        if (!videoCard.equals("-")) {
            if (i > 1) {
                stringBuilderSql.append(" AND ");
            }
            stringBuilderSql.append("video_card = ?");
        }

        return stringBuilderSql.toString();
    }

}
