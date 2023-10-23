package org.myapp.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Scanner;

public final class Handler implements HttpHandler {

    private final Controller controller;

    public Handler(Controller controller) {
        this.controller = controller;
        System.out.println("Handler");
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
//        System.out.println(httpExchange.getRequestURI());
//        System.out.println(httpExchange.getRequestMethod());
//        System.out.println(httpExchange.getRequestHeaders());
//        Scanner scanner = new Scanner(httpExchange.getRequestBody());
//        while (scanner.hasNext()) {
//            System.out.println(scanner.next());
//        }
        try (httpExchange) {
            controller.execute(httpExchange);
        } catch (final Exception e) {
            handleException(httpExchange);
            System.out.println(e);
        }
        System.out.println("handle");
    }

    private void handleException(final HttpExchange httpExchange) {
//        try {
//            httpExchange.sendResponseHeaders(STATUS_NOT_FOUND, 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("handleException");
    }
}
