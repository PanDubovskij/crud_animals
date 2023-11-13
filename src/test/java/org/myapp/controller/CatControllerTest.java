package org.myapp.controller;

import com.sun.net.httpserver.HttpHandler;
import org.junit.jupiter.api.*;
import org.myapp.dao.TestContainer;
import org.myapp.server.Server;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.myapp.connection.ConnectionPoolFabric.PropertiesFile.*;
import static org.myapp.controller.Constants.*;
import static org.myapp.util.Constants.CATS_PATH;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CatControllerTest {
    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static Server server;

    private static final String FULL_PATH = "http://localhost:8080/cats/";

    @BeforeAll
    void start() {
        postgreSQLContainer.start();
        Map<String, String> attributes = new HashMap<>();

        attributes.put(PASSWORD_KEY, postgreSQLContainer.getPassword());
        attributes.put(USERNAME_KEY, postgreSQLContainer.getUsername());
        attributes.put(URL_KEY, postgreSQLContainer.getJdbcUrl());
        attributes.put(POOL_SIZE, "24");
        Map<String, HttpHandler> handlers = new HashMap<>();
        handlers.put(CATS_PATH, new Handler(ControllerFactory.newCatController(attributes)));
        server = new Server(handlers);
        server.start();
    }

    @AfterAll
    void stop() {
        server.stop();
        postgreSQLContainer.stop();
    }

    @Test
    @Order(1)
    void search() {
        Client client = new Client.Builder()
                .url(FULL_PATH)
                .requestMethod(GET)
                .build();
        client.sendRequest();
        assertAll(() -> {
            assertEquals("""
                    {"owner-name":"jan","animals-number":1,"color":"red","name":"rizhiy","weight":5,"owner-age":21,"height":30}{"owner-name":"ksusha","animals-number":1,"color":"black-white","name":"varya","weight":5,"owner-age":43,"height":25}{"owner-name":"olga","animals-number":1,"color":"red-white","name":"nusha","weight":1,"owner-age":20,"height":15}""", client.getResponse());
            assertEquals(STATUS_OK, client.getResponseCode());
        });
    }

    @Test
    @Order(2)
    void create() {
        String content = """
                    {
                    "name": "testCat",
                    "color": "testColor",
                    "weight": 10,
                    "height": 50,
                    "owner-name": "jan",
                    "owner-age": 21
                }
                """;
        Client client = new Client.Builder()
                .url(FULL_PATH)
                .requestMethod(POST)
                .content(content)
                .build();
        client.sendRequest();
        assertEquals(STATUS_CREATED, client.getResponseCode());
    }

    @Test
    @Order(3)
    void update() {
        String content = """
                    {
                    "id" : 4,
                    "name": "testCat",
                    "weight": 1,
                    "height": 5,
                    "owner-name": "olga",
                    "owner-age": 20
                }
                """;
        String result = "{\"id\":4}";
        Client client = new Client.Builder()
                .url(FULL_PATH)
                .requestMethod(PUT)
                .content(content)
                .build();
        client.sendRequest();
        assertAll(() -> {

            assertEquals(STATUS_CREATED, client.getResponseCode());
            assertEquals(result, client.getResponse());
        });
    }

    @Test
    @Order(4)
    void delete() {
        Client client = new Client.Builder()
                .url(FULL_PATH + "?id=4")
                .requestMethod(DELETE)
                .build();
        client.sendRequest();
        assertEquals(STATUS_NO_CONTENT, client.getResponseCode());
    }
}