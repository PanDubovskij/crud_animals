package org.myapp.server;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.myapp.constants.Attributes;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.myapp.constants.Constants.*;

public abstract class Controller {
    protected abstract long create(final HttpExchange httpExchange);

    protected abstract List<JsonObject> search(final HttpExchange httpExchange);

    protected abstract long update(final HttpExchange httpExchange);

    protected abstract boolean delete(final HttpExchange httpExchange);

    public void execute(final HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        String requestType = httpExchange.getRequestURI().getPath();

        if (requestType.matches(REG_COMMON)) {

            switch (requestMethod) {
                case POST -> {
                    JsonObject jsonObject = new JsonObject();
                    long id = create(httpExchange);
                    jsonObject.put(Attributes.ID, id);
                    int status = id < 0 ? STATUS_NOT_FOUND : STATUS_CREATED;
                    writeResponse(httpExchange, List.of(jsonObject), status);
                }

                case GET -> {
                    List<JsonObject> search = search(httpExchange);
                    writeResponse(httpExchange, search, STATUS_OK);
                }

                case PUT -> {
                    JsonObject jsonObject = new JsonObject();
                    long id = update(httpExchange);
                    jsonObject.put(Attributes.ID, id);
                    int status = id < 0 ? STATUS_NOT_FOUND : STATUS_CREATED;
                    writeResponse(httpExchange, List.of(jsonObject), status);
                }

                case DELETE -> {
                    int typeRequest = delete(httpExchange) ? STATUS_NO_CONTENT : STATUS_NOT_FOUND;

                    response(httpExchange, typeRequest, -1);
                }
                default -> response(httpExchange, STATUS_BAD_REQUEST, -1);
            }
        } else {
            response(httpExchange, STATUS_BAD_REQUEST, -1);
        }
//        switch (requestMethod) {
//            case GET -> {
//                if (requestType.matches(REG_GET_SEARCH)) {
//                    List<JsonObject> search = search(httpExchange);
//                    writeResponse(httpExchange, search, STATUS_OK);
//                } else {
//                    response(httpExchange, STATUS_NOT_FOUND, -1);
//                }
//            }
//            case DELETE -> {
//                int typeRequest;
//                if (requestType.matches(REG_DELETE)) {
//                    typeRequest = delete(httpExchange) ? STATUS_NO_CONTENT : STATUS_NOT_FOUND;
//                } else {
//                    typeRequest = STATUS_NOT_FOUND;
//                }
//                response(httpExchange, typeRequest, -1);
//            }
//            case POST -> {
//                JsonObject jsonObject = new JsonObject();
//                long id;
//                int status;
//                if (requestType.matches(REG_POST_CREATE)) {
//                    id = create(httpExchange);
//                    jsonObject.put(Attributes.ID, id);
//                    status = id < 0 ? STATUS_NOT_FOUND : STATUS_CREATED;
//                    writeResponse(httpExchange, List.of(jsonObject), status);
//                } else {
////                    notSupportRequest(httpExchange, requestType, "Request type {} does noy support");
//                    response(httpExchange, STATUS_NOT_FOUND, -1);
//                }
//            }
//            case PUT -> {
//                JsonObject jsonObject = new JsonObject();
//                long id;
//                int status;
//                if (requestType.matches(REG_POST_UPDATE)) {
//                    id = update(httpExchange);
//                    jsonObject.put(Attributes.ID, id);
//                    status = id < 0 ? STATUS_NOT_FOUND : STATUS_CREATED;
//                    writeResponse(httpExchange, List.of(jsonObject), status);
//                } else {
//                    response(httpExchange, STATUS_NOT_FOUND, -1);
//                }
//            }
//            default -> notSupportRequest(httpExchange, requestMethod, "Method {} does not support");
//        }
        System.out.println("execute");
    }

    private void writeResponse(final HttpExchange httpExchange, List<JsonObject> jsonObjects, int status) throws IOException {

        StringBuilder body = new StringBuilder();
        for (JsonObject jsonObject : jsonObjects) {
            body.append(jsonObject.toJson());
        }

        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Content-type", "application/json");
        response(httpExchange, status, body.toString().getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(body.toString().getBytes(StandardCharsets.UTF_8));
            responseBody.flush();
        }
        System.out.println("writeResponse");
    }

    private void response(final HttpExchange httpExchange, final int status, final int responseLength) {
        try {
            httpExchange.sendResponseHeaders(status, responseLength);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("response");
    }

    private void notSupportRequest(final HttpExchange httpExchange, final String value, final String message) {
        try {
            httpExchange.sendResponseHeaders(STATUS_NOT_FOUND, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("notSupportRequest");
    }

    protected Optional<String> readAttributes(final URI uri, JsonKey key) {
        final String path = uri.getQuery();
        System.out.println("readAttributes");
        return path != null ? Arrays.stream(path.split(REG_ATTRIBUTES_DELIMITER))
                .filter(a -> a.contains(key.getKey()))
                .map(p -> p.split(REG_ATTRIBUTE_DELIMITER)[1])
                .findFirst() : Optional.empty();
    }

    protected JsonObject readRequestFromJson(final HttpExchange httpExchange) {
        JsonObject deserialized = null;
        try (InputStream requestBody = httpExchange.getRequestBody();
             BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, StandardCharsets.UTF_8))) {
            if (reader.ready()) {
                deserialized = (JsonObject) Jsoner.deserialize(reader);
            }
        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
        System.out.println("readRequestFromJson");
        return deserialized;
    }

    protected void searchAttributeUrl(URI requestURI, Map<Attributes, String> attributes) {
        Arrays.stream(Attributes.values())
                .forEach(at -> {
                    Optional<String> attribute = readAttributes(requestURI, at);
                    attribute.ifPresent(p -> attributes.put(at, p));
                });
        System.out.println("searchAttributeUrl");
    }

}
