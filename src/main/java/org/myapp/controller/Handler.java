package org.myapp.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Handler implements HttpHandler {

    private final Controller controller;

    private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

    public Handler(Controller controller) {
        this.controller = controller;
    }

    /**
     * Handle requests and responses via provided {@link Controller} and {@link HttpExchange}
     *
     * @param httpExchange the exchange containing the request from the
     *                     client and used to send the response
     */
    @Override
    public void handle(final HttpExchange httpExchange) {
        try (httpExchange) {
            controller.execute(httpExchange);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, "can't execute request", e);
        }
    }
}
