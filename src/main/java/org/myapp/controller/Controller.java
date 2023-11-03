package org.myapp.controller;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.myapp.controller.Constants.*;

public abstract class Controller {

    protected static final Logger logger = Logger.getLogger(Controller.class.getName());

    protected abstract JsonObject create(final HttpExchange httpExchange);

    protected abstract List<JsonObject> search(final HttpExchange httpExchange);

    protected abstract JsonObject update(final HttpExchange httpExchange);

    protected abstract boolean delete(final HttpExchange httpExchange);

    public void execute(final HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        String requestType = httpExchange.getRequestURI().getPath();

        logger.info("request method: " + requestMethod + " and request path: " + requestType);

        if (requestType.matches(REG_COMMON_ENDPOINT)) {
            logger.info("path matches");
            switch (requestMethod) {
                case POST -> {
                    JsonObject jsonObject = create(httpExchange);
                    long id = jsonObject.getLong(Attributes.ID);
                    logger.info("created id: " + id);
                    int status = id < 0 ? STATUS_BAD_REQUEST : STATUS_CREATED;
                    writeResponse(httpExchange, List.of(jsonObject), status);
                }

                case GET -> {
                    List<JsonObject> search = search(httpExchange);
                    writeResponse(httpExchange, search, STATUS_OK);
                }

                case PUT -> {
                    JsonObject jsonObject = update(httpExchange);
                    long id = jsonObject.getLong(Attributes.ID);
                    logger.info("updated id: " + id);
                    int status = id < 0 ? STATUS_NOT_FOUND : STATUS_CREATED;
                    writeResponse(httpExchange, List.of(jsonObject), status);
                }

                case DELETE -> {
                    int typeRequest = delete(httpExchange) ? STATUS_NO_CONTENT : STATUS_NOT_FOUND;
                    response(httpExchange, typeRequest, -1);
                }
                default -> {
                    logger.warning("method doesn't match");
                    response(httpExchange, STATUS_BAD_REQUEST, -1);
                }
            }
        } else {
            logger.warning("path doesn't match");
            response(httpExchange, STATUS_BAD_REQUEST, -1);
        }
    }

    private void writeResponse(final HttpExchange httpExchange, List<JsonObject> jsonObjects, int status) throws IOException {

        StringBuilder body = new StringBuilder();
        for (JsonObject jsonObject : jsonObjects) {
            body.append(jsonObject.toJson());
        }
        logger.info(body.toString());
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Content-type", "application/json");
        response(httpExchange, status, body.toString().getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(body.toString().getBytes(StandardCharsets.UTF_8));
            responseBody.flush();
        }
    }

    private void response(final HttpExchange httpExchange, final int status, final int responseLength) {
        try {
            httpExchange.sendResponseHeaders(status, responseLength);
        } catch (IOException e) {
            logger.log(Level.WARNING, "some issues with writing response", e);
        }
    }

    protected Optional<String> readAttributes(final URI uri, JsonKey key) {
        final int VALUE = 1;
        final String path = uri.getQuery();
        Optional<String> attributes = path != null ? Arrays.stream(path.split(REG_ATTRIBUTES_DELIMITER))
                .filter(a -> a.contains(key.getKey()))
                .map(p -> p.split(REG_ATTRIBUTE_DELIMITER)[VALUE])
                .findFirst() : Optional.empty();

        attributes.ifPresent(logger::info);
        if (attributes.isEmpty()) {
            logger.warning("nothing to read");
        }
        return attributes;
    }

    protected Optional<JsonObject> readRequestFromJson(final HttpExchange httpExchange) {
        Optional<JsonObject> deserialized = Optional.empty();

        try (InputStream requestBody = httpExchange.getRequestBody();
             BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, StandardCharsets.UTF_8))) {
            if (reader.ready()) {
                deserialized = Optional.ofNullable((JsonObject) Jsoner.deserialize(reader));
            }
        } catch (IOException | JsonException e) {
            logger.log(Level.WARNING, "can't deserialize json", e);
        }
        return deserialized;
    }

}
