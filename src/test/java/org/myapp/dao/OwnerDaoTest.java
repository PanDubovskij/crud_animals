package org.myapp.dao;

import org.junit.jupiter.api.*;
import org.myapp.entity.Owner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.myapp.connection.ConnectionPoolFabric.PropertiesFile.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OwnerDaoTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static OwnerDao ownerDao;

    @BeforeAll
    void start() {
        postgreSQLContainer.start();
        Map<String, String> attributes = new HashMap<>();
        attributes.put(PASSWORD_KEY, postgreSQLContainer.getPassword());
        attributes.put(USERNAME_KEY, postgreSQLContainer.getUsername());
        attributes.put(URL_KEY, postgreSQLContainer.getJdbcUrl());
        attributes.put(POOL_SIZE, "24");
        ownerDao = new OwnerDao(attributes);
    }

    @AfterAll
    void stop() {
        postgreSQLContainer.stop();
    }

    @Test
    @Order(1)
    void search() {
        List<Owner> result = ownerDao.search();
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    @Order(2)
    void create() {
        Owner testOwner = new Owner.Builder()
                .setId(4L)
                .setName("testCat")
                .setAge(100)
                .build();
        long ownerId = ownerDao.create(testOwner);
        assertEquals(4L, ownerId);
        List<Owner> search = ownerDao.search();
        Optional<Owner> optionalOwner = ownerDao.searchBy(4L);
        assertTrue(optionalOwner.isPresent());
        assertEquals(4, search.size());
    }

    @Test
    @Order(3)
    void searchBy() {
        Optional<Owner> optionalOwner = ownerDao.searchBy(4L);
        assertNotNull(optionalOwner);
        assertTrue(optionalOwner.isPresent());
    }

    @Test
    @Order(4)
    void update() {
        Owner testOwner = new Owner.Builder()
                .setId(4L)
                .setName("testOwner")
                .setAge(101)
                .build();
        long ownerId = ownerDao.update(testOwner);
        assertEquals(4L, ownerId);
        List<Owner> search = ownerDao.search();
        Optional<Owner> optionalOwner = ownerDao.searchBy(4L);
        assertTrue(optionalOwner.isPresent());
        assertEquals(4, search.size());
    }

    @Test
    @Order(5)
    void delete() {
        boolean flag = ownerDao.delete(4L);
        List<Owner> search = ownerDao.search();
        Optional<Owner> optionalOwner = ownerDao.searchBy(4L);
        assertEquals(flag, optionalOwner.isEmpty());
        assertEquals(3, search.size());
    }
}