package org.myapp.dao;

import org.myapp.entity.Owner;
import org.myapp.util.ConnectionPool;
import org.myapp.util.ConnectionPoolFabric;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.myapp.dao.Constants.*;

public final class OwnerDao implements Dao<Owner> {

    private static ConnectionPool connectionPool;

    public OwnerDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection();
        }
    }

    public OwnerDao(Map<String, String> attributes) {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection(attributes);
        }
    }

    @Override
    public long create(final Owner owner) {
        long ownerId = getOwnerId(owner);
        if (INVALID_ID == ownerId) {
            ownerId = createIfNotExist(owner);
        }
        return ownerId;
    }

    private long getOwnerId(final Owner owner) {
        long ownerId = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OWNER_ID)) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setInt(2, owner.getAge());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ownerId = resultSet.getLong(OWNER_ID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ownerId;
    }

    private long createIfNotExist(final Owner owner) {
        long ownerID = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OWNER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setInt(2, owner.getAge());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ownerID = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ownerID;
    }

    @Override
    public List<Owner> search() {
        List<Owner> owners = new ArrayList<>();

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_OWNERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                owners.add(resultSetToOwner(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owners;
    }

    @Override
    public Owner searchById(final long id) {

        Owner owner = null;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OWNER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                owner = resultSetToOwner(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owner;
    }

    @Override
    public long update(final Owner owner) {
        long ownerID = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OWNER)) {
            preparedStatement.setInt(1, owner.getAge());
            preparedStatement.setLong(2, owner.getId());
            ownerID = preparedStatement.executeUpdate() == 1 ? owner.getId() : ownerID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ownerID;
    }

    @Override
    public boolean delete(final long id) {
        int result;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OWNER)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result == 1;
    }

    private static Owner resultSetToOwner(final ResultSet resultSet) throws SQLException {
        return new Owner.Builder()
                .setId(resultSet.getLong(OWNER_ID))
                .setName(resultSet.getString(OWNER_NAME))
                .setAge(resultSet.getInt(OWNER_AGE))
                .setAnimalsAmount(resultSet.getInt(OWNER_ANIMALS_AMOUNT))
                .build();
    }

    private static final String SELECT_OWNER_ID = """
            SELECT owner_id FROM owner WHERE owner_name=? AND owner_age=? LIMIT 1;
            """;

    private static final String INSERT_OWNER = """
            INSERT INTO owner (owner_name, owner_age) VALUES (?, ?);
            """;

    private static final String SELECT_ALL_OWNERS = """
            SELECT * FROM owner;
            """;

    private static final String SELECT_OWNER_BY_ID = """
            SELECT * FROM owner WHERE owner_id=?;
            """;

    private static final String UPDATE_OWNER = """
              UPDATE owner SET owner_age=? WHERE owner_id=?;
            """;

    private static final String DELETE_OWNER = """
            DELETE FROM owner WHERE owner_id =?;
            """;
}
