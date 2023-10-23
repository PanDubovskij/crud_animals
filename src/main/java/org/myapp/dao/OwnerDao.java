package org.myapp.dao;

import org.myapp.entity.Cat;
import org.myapp.entity.Owner;
import org.myapp.utils.ConnectionPool;

import java.sql.*;
import java.util.List;

public class OwnerDao<T extends Owner> implements Dao<Owner> {

    private static ConnectionPool connectionPool;

    public OwnerDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.INSTANCE;
        }
    }

    @Override
    public long create(Owner owner) {
        long ownerID = -1;
        String command = """
                INSERT INTO owner (owner_name, owmer_age) VALUES (?, ?);
                """;
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
//            Object[] seq = {
//                    Integer.parseInt(cat.getValue()),
//                    cat.getUnit(),
//                    cat.getVoltage(),
//                    cat.getCaseValue(),
//                    cat.getTempHigh(),
//                    cat.getTempLow()
//            };
//            JdbcUtil.setStatement(preparedStatement, seq);
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
        return null;
    }

    @Override
    public long update(Owner owner) {
        return 0;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
