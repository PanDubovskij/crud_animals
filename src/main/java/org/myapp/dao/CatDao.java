package org.myapp.dao;

import org.myapp.entity.Cat;
import org.myapp.connection.ConnectionPool;
import org.myapp.connection.ConnectionPoolFabric;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.myapp.dao.Constants.*;
import static org.myapp.util.Constants.INVALID_ID;

public final class CatDao implements Dao<Cat> {
    private static ConnectionPool connectionPool;

    private static final Logger logger = Logger.getLogger(CatDao.class.getName());

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
        long catId = INVALID_ID;

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
                    catId = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "some issues with sql", e);
        }
        logger.info("cat id: " + catId);
        return catId;
    }

    @Override
    public List<Cat> search() {
        List<Cat> cats = new ArrayList<>();

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cats.add(catFrom(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "some issues with sql", e);
        }
        return cats;
    }

    @Override
    public Optional<Cat> searchBy(final long id) {
        Optional<Cat> optionalCat = Optional.empty();

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalCat = Optional.of(catFrom(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "some issues with sql", e);
        }
        optionalCat.ifPresent(cat -> logger.info(cat.toString()));
        if (optionalCat.isEmpty()) {
            logger.warning("nothing to read");
        }
        return optionalCat;
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
            logger.log(Level.WARNING, "some issues with sql", e);
        }
        logger.info("cat id: " + catId);
        return catId;
    }

    @Override
    public boolean delete(final long id) {
        final int INVALID_RESULT = -1;
        int result = INVALID_RESULT;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAT)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "some issues with sql", e);
        }
        logger.info("deleted rows: " + result);
        return result == 1;
    }

    private Cat catFrom(final ResultSet resultSet) throws SQLException {
        return new Cat.Builder()
                .setId(resultSet.getLong(CAT_ID))
                .setName(resultSet.getString(CAT_NAME))
                .setColor(resultSet.getString(CAT_COLOR))
                .setWeight(resultSet.getInt(CAT_WEIGHT))
                .setHeight(resultSet.getInt(CAT_HEIGHT))
                .setOwnerId(resultSet.getLong(OWNER_ID))
                .build();
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
