package org.myapp.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handler implements HttpHandler {

    private Controller controller;
    @Override
    public void handle(final HttpExchange httpExchange) {
//        try {
//            controller.execute(httpExchange);
//        } catch (final Exception e) {
//            handleException(httpExchange);
//        } finally {
//            httpExchange.close();
//        }
    }

    private void handleException(final HttpExchange httpExchange) {
//        try {
//            httpExchange.sendResponseHeaders(STATUS_NOT_FOUND, 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
