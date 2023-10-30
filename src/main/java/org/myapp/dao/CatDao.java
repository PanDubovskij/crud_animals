package org.myapp.dao;

import org.myapp.entity.Cat;
import org.myapp.utils.ConnectionPool;
import org.myapp.utils.ConnectionPoolFabric;
import org.myapp.utils.ResultSetEntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CatDao implements Dao<Cat> {

    private static ConnectionPool connectionPool;

    public CatDao(final Map<String, String> attributes) {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection(attributes);
        }
    }

    public CatDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection();
        }
    }

    @Override
    public long create(final Cat cat) {
        long catID = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, cat.getColor());
            preparedStatement.setInt(3, cat.getWeight());
            preparedStatement.setInt(4, cat.getHeight());
            preparedStatement.setLong(5, cat.getOwnerId());
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

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cats.add(ResultSetEntityMapper.resultSetToCat(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cats;
    }

    @Override
    public Cat searchById(final long id) {

        Cat cat = null;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cat = ResultSetEntityMapper.resultSetToCat(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cat;
    }

    @Override
    public long update(final Cat cat) {
        long catId = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CAT)) {
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setInt(2, cat.getWeight());
            preparedStatement.setInt(3, cat.getHeight());
            preparedStatement.setLong(4, cat.getOwnerId());
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
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAT)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result == 1;
    }

    private static final String INSERT_CAT = """
            INSERT INTO cat (cat_name, color, weight, height, owner_id) VALUES (?, ?, ?, ?, ?);
            """;
    private static final String SELECT_ALL_CATS = """
            SELECT * FROM cat;
            """;
    private static final String SELECT_CAT_BY_ID = """
            SELECT * FROM cat WHERE cat_id=?;
            """;
    private static final String UPDATE_CAT = """
              UPDATE cat SET cat_name=?, weight=?, height=?, owner_id=? WHERE cat_id=?;
            """;
    private static final String DELETE_CAT = """
            DELETE FROM cat WHERE cat_id =?;
            """;
}
