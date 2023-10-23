package org.myapp.dao;

import org.myapp.entity.Cat;
import org.myapp.utils.ConnectionPool;

import java.sql.*;
import java.util.List;

public class CatDao<T extends Cat> implements Dao<Cat> {

    private static ConnectionPool connectionPool;

    public CatDao() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.INSTANCE;
        }
    }

    @Override
    public long create(Cat cat) {
        long catID = -1;
        String command = """
                INSERT INTO cat (cat_name, color, weight, height, owner_id) VALUES (?, ?, ?, ?, ?);
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
        return null;
    }

    @Override
    public long update(Cat cat) {
        return 0;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
