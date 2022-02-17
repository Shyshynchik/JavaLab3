package mysql.service;

import mysql.connection.MySQLConnection;
import mysql.dao.DAOAccessories;
import mysql.entity.VideoCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoCardService extends MySQLConnection implements DAOAccessories<VideoCard> {

    private final Connection connection;

    public VideoCardService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public VideoCard getById(Integer id) {
        PreparedStatement statement = null;

        String sql = "select * from video_card where id=?";

        VideoCard videoCard = new VideoCard();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                videoCard.setId(resultSet.getInt("id"));
                videoCard.setName(resultSet.getString("name"));
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

        return videoCard;
    }

    @Override
    public VideoCard getByNameOrAdd(String name) {
        PreparedStatement statement = null;

        String sql = "select * from video_card where name=?";

        VideoCard videoCard = new VideoCard();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                videoCard.setId(resultSet.getInt("id"));
                videoCard.setName(resultSet.getString("name"));
            } else {
                videoCard = add(name);
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

        return videoCard;
    }

    @Override
    public List<VideoCard> getAll() {
        ArrayList<VideoCard> osArrayList = new ArrayList<>();
        PreparedStatement statement = null;

        String sql = "select * from video_card";

        try {
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                VideoCard videoCard = new VideoCard();
                videoCard.setId(resultSet.getInt("id"));
                videoCard.setName(resultSet.getString("name"));

                osArrayList.add(videoCard);
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
    public VideoCard add(String name) {
        VideoCard videoCard = new VideoCard();

        PreparedStatement statement = null;
        String sql = "INSERT INTO video_card (name) " + "VALUES (?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            videoCard = getById(resultSet.getInt(1));

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

        return videoCard;
    }
}
