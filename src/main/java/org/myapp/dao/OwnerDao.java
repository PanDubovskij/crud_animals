package org.myapp.dao;

import org.myapp.connection.ConnectionPool;
import org.myapp.connection.ConnectionPoolFabric;
import org.myapp.entity.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.myapp.dao.Constants.*;
import static org.myapp.util.Constants.INVALID_ID;

/**
 * This class works with {@link Owner} entities.
 */
public final class OwnerDao implements Dao<Owner> {
    private static ConnectionPool connectionPool;

    private static final Logger LOGGER = Logger.getLogger(OwnerDao.class.getName());

    /**
     * Constructor with default connection
     */
    public OwnerDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection();
        }
    }

    /**
     * @param attributes of your connection to db
     */
    public OwnerDao(Map<String, String> attributes) {
        if (connectionPool == null) {
            connectionPool = ConnectionPoolFabric.createConnection(attributes);
        }
    }

    /**
     * Create owner if not exists otherwise return id of existing owner
     *
     * @param owner entity need to be created.
     * @return id of created owner, if something went wrong return -1.
     */
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
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        LOGGER.info("owner id: " + ownerId);
        return ownerId;
    }

    private long createIfNotExist(final Owner owner) {
        long ownerId = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OWNER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setInt(2, owner.getAge());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ownerId = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        LOGGER.info("owner id: " + ownerId);
        return ownerId;
    }

    /**
     * Search all owners.
     *
     * @return List of all exciting owners, if there's no owners then return empty list.
     */
    @Override
    public List<Owner> search() {
        List<Owner> owners = new ArrayList<>();

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_OWNERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                owners.add(ownerFrom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        return owners;
    }

    /**
     * Search owner by id.
     *
     * @param id of required owner
     * @return {@link Optional} with owner if there's owner with given id, otherwise return empty Optional
     */
    @Override
    public Optional<Owner> searchBy(final long id) {

        Optional<Owner> optionalOwner = Optional.empty();
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OWNER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalOwner = Optional.of(ownerFrom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        optionalOwner.ifPresent(owner -> LOGGER.info(owner.toString()));
        if (optionalOwner.isEmpty()) {
            LOGGER.warning("nothing to search");
        }
        return optionalOwner;
    }

    /**
     * Update owner with given id
     *
     * @param owner new entity need to be replaced with old one
     * @return return id of updated owner, if something went wrong return -1
     */
    @Override
    public long update(final Owner owner) {
        long ownerId = INVALID_ID;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OWNER)) {
            preparedStatement.setInt(1, owner.getAge());
            preparedStatement.setLong(2, owner.getId());
            ownerId = preparedStatement.executeUpdate() == 1 ? owner.getId() : ownerId;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        LOGGER.info("owner id: " + ownerId);
        return ownerId;
    }

    /**
     * Delete owner by id.
     *
     * @param id provided id of entity to be deleted
     * @return return true if owner was deleted, otherwise false
     */
    @Override
    public boolean delete(final long id) {
        final int INVALID_RESULT = -1;
        int result = INVALID_RESULT;

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OWNER)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "some issues with sql", e);
        }
        LOGGER.info("deleted rows: " + result);
        return result == 1;
    }

    private Owner ownerFrom(final ResultSet resultSet) throws SQLException {
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
