package org.myapp.connection;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum ConnectionPool {
    INSTANCE();
    private static final Integer DEFAULT_POOL_SIZE = 24;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());

    private String urlKey;
    private String usernameKey;
    private String passwordKey;
    private String poolSize;

    private BlockingQueue<Connection> pool;
    private List<Connection> sourceConnection;


    public ConnectionPool urlKey(String urlKey) {
        this.urlKey = urlKey;
        return this;
    }

    public ConnectionPool usernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
        return this;
    }

    public ConnectionPool passwordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public ConnectionPool poolSize(String poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public ConnectionPool build() {
        String poolSizeValue = poolSize;
        int size = poolSizeValue == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSizeValue);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            creatProxyConnection();
        }
        return this;
    }

    public Connection openConnection() {
        Connection connection = null;
        try {
            connection = pool.poll(1, TimeUnit.SECONDS);
            if (!(connection != null && connection.isValid(1))) {
                creatProxyConnection();
                connection = openConnection();
            }
        } catch (InterruptedException | SQLException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "thread interrupted or timeout < 0", e);
        }
        return connection;
    }

    public void destroyPool() {
        for (Connection connection : sourceConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "can't close connection", e);
            }
        }
    }

    private void creatProxyConnection() {
        final Connection connection;
        try {
            connection = postgreSqlDataSource().getConnection();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionPool.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> "close".equals(method.getName()) ? pool.add((Connection) proxy) : method.invoke(connection, args)
            );
            pool.add(proxyConnection);
            sourceConnection.add(connection);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "can't get connection from db", e);
        }
    }

    private DataSource postgreSqlDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(urlKey);
        dataSource.setUser(usernameKey);
        dataSource.setPassword(passwordKey);
        return dataSource;
    }
}
