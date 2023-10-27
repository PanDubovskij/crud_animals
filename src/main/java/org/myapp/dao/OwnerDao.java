package org.myapp.dao;

import org.myapp.entity.Owner;
import org.myapp.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class OwnerDao implements Dao<Owner> {

    private static ConnectionPool connectionPool;

    public OwnerDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.INSTANCE
                    .urlKey("jdbc:postgresql://localhost:5432/postgres")
                    .passwordKey("postgres")
                    .usernameKey("postgres")
                    .poolSize("5")
                    .build();
        }
    }

    @Override
    public long create(final Owner owner) {
        long ownerId = getOwnerId(owner);
        if (-1 == ownerId) {
            ownerId = createIfNotExist(owner);
        }
        return ownerId;
    }

    private long getOwnerId(final Owner owner) {
        long ownerId = -1;
        String query = """
                SELECT owner_id FROM owner WHERE owner_name=? AND owner_age=? LIMIT 1;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setInt(2, owner.getAge());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ownerId = resultSet.getLong("owner_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ownerId;
    }

    private long createIfNotExist(final Owner owner) {
        long ownerID = -1;
        String command = """
                INSERT INTO owner (owner_name, owner_age) VALUES (?, ?);
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
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
        String query = """
                SELECT * FROM owner;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                owners.add(new Owner.Builder()
                        .setId(resultSet.getLong("owner_id"))
                        .setName(resultSet.getString("owner_name"))
                        .setAge(resultSet.getInt("owner_age"))
                        .setAnimalsAmount(resultSet.getInt("animals_amount"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owners;
    }

    @Override
    public Owner searchById(final long id) {
        String query = """
                SELECT * FROM owner WHERE owner_id=?;
                """;
        Owner owner = null;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                owner = new Owner.Builder()
                        .setId(resultSet.getLong("owner_id"))
                        .setName(resultSet.getString("owner_name"))
                        .setAge(resultSet.getInt("owner_age"))
                        .setAnimalsAmount(resultSet.getInt("animals_amount"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owner;
    }

    @Override
    public long update(final Owner owner) {
        long ownerID = -1;
        String sql = """
                  UPDATE owner SET owner_age=? WHERE owner_id=?;
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        String delete = """
                DELETE FROM owner WHERE owner_id =?;
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
