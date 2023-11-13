package org.myapp.dao;

import org.myapp.connection.ConnectionPool;
import org.myapp.connection.ConnectionPoolFabric;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.myapp.connection.ConnectionPoolFabric.PropertiesFile.*;
import static org.myapp.dao.TestContainer.InitDB.createDb;
import static org.myapp.dao.TestContainer.InitDB.destroyDb;

public class TestContainer extends PostgreSQLContainer<TestContainer> {
    private static final Map<String, String> attributes = new HashMap<>();
    private static TestContainer container;
    private static ConnectionPool connectionPool;

    private TestContainer() {
        super("postgres:latest");
    }

    @Override
    public void start() {
        super.start();
        attributes.put(PASSWORD_KEY, container.getPassword());
        attributes.put(USERNAME_KEY, container.getUsername());
        attributes.put(URL_KEY, container.getJdbcUrl());
        attributes.put(POOL_SIZE, "24");
        connectionPool = ConnectionPoolFabric.createConnection(attributes);
        try (Connection connection = connectionPool.openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(destroyDb);
            statement.execute(createDb);
            statement.executeUpdate(insertDb);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "something went wrong with db!", e);
        }
    }

    @Override
    public void stop() {
        super.stop();
        connectionPool.destroyPool();
    }

    public static TestContainer getContainer() {
        if (container == null) {
            container = new TestContainer();
        }
        return container;
    }


    static class InitDB {

        public static final String destroyDb = """
                DROP TABLE IF EXISTS cat;
                DROP TABLE IF EXISTS owner;
                """;
        public static final String createDb = """
                create table if not exists owner
                  (
                      owner_id       bigserial
                          primary key,
                      owner_name     varchar           not null,
                      owner_age      integer           not null,
                      animals_amount integer default 0 not null
                  );
                  
                  create table if not exists cat
                  (
                      cat_id   bigserial
                          primary key,
                      cat_name varchar not null,
                      color    varchar not null,
                      weight   integer not null,
                      height   integer not null,
                      owner_id bigserial
                          constraint cat_owner_fk
                              references owner
                              on update cascade on delete cascade
                  );

                  create or replace function update_animals_amount() returns trigger
                      language plpgsql
                  as
                  $$
                  BEGIN
                      IF TG_OP = 'INSERT' THEN
                          -- Increment animals_amount when inserting a new cat
                          UPDATE owner
                          SET animals_amount = animals_amount + 1
                          WHERE owner_id = NEW.owner_id;
                  	ELSIF TG_OP = 'UPDATE' THEN
                    	  UPDATE owner
                          SET animals_amount = animals_amount - 1
                          WHERE OLD.owner_id<>NEW.owner_id AND owner_id = OLD.owner_id;
                    	  UPDATE owner
                          SET animals_amount = animals_amount + 1
                          WHERE OLD.owner_id<>NEW.owner_id AND owner_id = NEW.owner_id;
                      ELSIF TG_OP = 'DELETE' THEN
                          -- Decrement animals_amount when deleting a cat
                          UPDATE owner
                          SET animals_amount = animals_amount - 1
                          WHERE owner_id = OLD.owner_id;
                      END IF;
                      RETURN NEW;
                  END;
                  $$;
                                    
                  create trigger update_animals_amount_trigger
                      after insert or update of owner_id or delete
                      on cat
                      for each row
                  execute procedure update_animals_amount();
                  """;
    }

    public static final String insertDb = """
            INSERT INTO owner(owner_name, owner_age)
            VALUES ('jan', 21),
                   ('ksusha', 43),
                   ('olga', 20);
            INSERT INTO cat(cat_name, color, weight, height, owner_id)
            VALUES ('rizhiy', 'red', 5, 30, 1),
                   ('varya', 'black-white', 5, 25, 2),
                   ('nusha', 'red-white', 1, 15, 3);
                   """;
}
