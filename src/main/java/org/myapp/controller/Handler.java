package org.myapp.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Handler implements HttpHandler {

    private final Controller controller;

    private static final Logger logger = Logger.getLogger(Handler.class.getName());

    public Handler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(final HttpExchange httpExchange) {
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
//            handleException(httpExchange);
            logger.log(Level.WARNING, "can't execute request", e);
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
