package org.myapp.connection;

import org.myapp.util.PropertiesUtil;

import java.util.Map;

import static org.myapp.connection.ConnectionPoolFabric.PropertiesFile.*;

public class ConnectionPoolFabric {

    private ConnectionPoolFabric() {
    }

    public static class PropertiesFile {

        public static final String PASSWORD_KEY = "db.password";
        public static final String USERNAME_KEY = "db.username";
        public static final String URL_KEY = "db.url";
        public static final String POOL_SIZE = "db.pool";

    }

    public static ConnectionPool createConnection() {
        return ConnectionPool.INSTANCE
                .urlKey(PropertiesUtil.get(URL_KEY))
                .passwordKey(PropertiesUtil.get(PASSWORD_KEY))
                .usernameKey(PropertiesUtil.get(USERNAME_KEY))
                .poolSize(PropertiesUtil.get(POOL_SIZE))
                .build();
    }

    public static ConnectionPool createConnection(Map<String, String> properties) {
        return ConnectionPool.INSTANCE
                .urlKey(properties.get(URL_KEY))
                .passwordKey(properties.get(PASSWORD_KEY))
                .usernameKey(properties.get(USERNAME_KEY))
                .poolSize(properties.get(POOL_SIZE))
                .build();
    }

}
