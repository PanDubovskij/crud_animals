package org.myapp.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public final class Handler implements HttpHandler {

    private final Controller controller;

    public Handler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(final HttpExchange httpExchange) {
        try (httpExchange) {
            controller.execute(httpExchange);
        } catch (final Exception e) {
            handleException(httpExchange);
        }
    }

    private void handleException(final HttpExchange httpExchange) {
//        try {
//            httpExchange.sendResponseHeaders(STATUS_NOT_FOUND, 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
