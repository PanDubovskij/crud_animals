package org.myapp.dao;

import org.myapp.entity.Cat;
import org.myapp.entity.Owner;
import org.myapp.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class CatDao implements Dao<Cat> {

    private static ConnectionPool connectionPool;

    public CatDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.INSTANCE;
        }
    }

    @Override
    public long create(final Cat cat) {
        long catID = -1;
        String command = """
                INSERT INTO cat (cat_name, color, weight, height, owner_id) VALUES (?, ?, ?, ?, ?);
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, cat.getColor());
            preparedStatement.setInt(3, cat.getWeight());
            preparedStatement.setInt(4, cat.getHeight());
            preparedStatement.setLong(5, cat.getOwner().getId());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    catID = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return catID;
    }

    @Override
    public List<Cat> search() {
        List<Cat> cats = new ArrayList<>();
        String query = """
                SELECT * FROM cat;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cats.add(new Cat.Builder()
                        .setId(resultSet.getLong("cat_id"))
                        .setName(resultSet.getString("cat_name"))
                        .setColor(resultSet.getString("color"))
                        .setWeight(resultSet.getInt("weight"))
                        .setHeight(resultSet.getInt("height"))
                        .setOwner(new Owner.Builder()
                                .setId(resultSet.getLong("owner_id"))
//                                .setName()
//                                .setAge()
//                                .setAnimalsAmount()
                                .build())
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cats;
    }

    @Override
    public long update(final Cat cat) {
        long catId = -1;
        String sql = """
                  UPDATE cat SET cat_name=?, color=?, weight=?, height=? WHERE cat_id=?;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, cat.getColor());
            preparedStatement.setInt(3, cat.getWeight());
            preparedStatement.setInt(4, cat.getHeight());
            preparedStatement.setLong(5, cat.getId());
            catId = preparedStatement.executeUpdate() == 1 ? cat.getId() : catId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return catId;
    }

    @Override
    public boolean delete(final long id) {
        int result;
        String delete = """
                DELETE FROM cat WHERE cat_id =?;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result == 1;
    }
}