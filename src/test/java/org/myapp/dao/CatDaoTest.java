package org.myapp.dao;

import org.junit.jupiter.api.*;
import org.myapp.entity.Cat;
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
class CatDaoTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static CatDao catDao;

    @BeforeAll
    void start() {
        postgreSQLContainer.start();
        Map<String, String> attributes = new HashMap<>();
        attributes.put(PASSWORD_KEY, postgreSQLContainer.getPassword());
        attributes.put(USERNAME_KEY, postgreSQLContainer.getUsername());
        attributes.put(URL_KEY, postgreSQLContainer.getJdbcUrl());
        attributes.put(POOL_SIZE, "24");
        catDao = new CatDao(attributes);
    }

    @AfterAll
    void stop() {
        postgreSQLContainer.stop();
    }

    @Test
    @Order(1)
    void search() {
        List<Cat> result = catDao.search();
        assertNotNull(result);
        long id = 1L;
        for (Cat cat : result) {
            assertEquals(id, cat.getId());
            id++;
        }
    }

    @Test
    @Order(2)
    void create() {
        Cat testCat = new Cat.Builder()
                .setId(4L)
                .setName("testCat")
                .setColor("C")
                .setWeight(10)
                .setHeight(50)
                .setOwnerId(1L)
                .build();
        long catId = catDao.create(testCat);
        assertEquals(4L, catId);
        List<Cat> search = catDao.search();
        Optional<Cat> optionalCat = catDao.searchBy(4L);
        assertTrue(optionalCat.isPresent());
        assertEquals(4, search.size());
    }

    @Test
    @Order(3)
    void searchBy() {
        Optional<Cat> optionalCat = catDao.searchBy(4L);
        assertNotNull(optionalCat);
        assertTrue(optionalCat.isPresent());
    }

    @Test
    @Order(4)
    void update() {
        Cat testCat = new Cat.Builder()
                .setId(4L)
                .setName("testCat")
                .setWeight(1)
                .setHeight(5)
                .setOwnerId(2L)
                .build();
        long catId = catDao.update(testCat);
        assertEquals(4L, catId);
        List<Cat> search = catDao.search();
        Optional<Cat> optionalCat = catDao.searchBy(4L);
        assertTrue(optionalCat.isPresent());
        assertEquals(4, search.size());
    }

    @Test
    @Order(5)
    void delete() {
        boolean flag = catDao.delete(4L);
        List<Cat> search = catDao.search();
        Optional<Cat> optionalCat = catDao.searchBy(4L);
        assertEquals(flag, optionalCat.isEmpty());
        assertEquals(3, search.size());
    }
}