package org.myapp.utils;

import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.myapp.dao.Constants.*;

public final class ResultSetEntityMapper {
    public static Cat resultSetToCat(final ResultSet resultSet) throws SQLException {
        return new Cat.Builder()
                .setId(resultSet.getLong(CAT_ID))
                .setName(resultSet.getString(CAT_NAME))
                .setColor(resultSet.getString(CAT_COLOR))
                .setWeight(resultSet.getInt(CAT_WEIGHT))
                .setHeight(resultSet.getInt(CAT_HEIGHT))
                .setOwnerId(resultSet.getLong(OWNER_ID))
                .build();
    }

    public static Owner resultSetToOwner(final ResultSet resultSet) throws SQLException {
        return new Owner.Builder()
                .setId(resultSet.getLong(OWNER_ID))
                .setName(resultSet.getString(OWNER_NAME))
                .setAge(resultSet.getInt(OWNER_AGE))
                .setAnimalsAmount(resultSet.getInt(OWNER_ANIMALS_AMOUNT))
                .build();
    }
}
