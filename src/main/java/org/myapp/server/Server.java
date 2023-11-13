package org.myapp.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Something like facade of Sun's {@link HttpServer}
 */
public final class Server {
    private HttpServer server;
    private final Map<String, HttpHandler> handlers = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(Map<String, HttpHandler> handlers) {
        this.handlers.putAll(handlers);
    }


    /**
     * Bind server context with handlers and start server
     */
    public void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            handlers.forEach((path, hand) -> server.createContext(path, hand));
            server.start();
            LOGGER.info("server started");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "some issues with server", e);
        }
    }

    /**
     * stop the server
     */
    public void stop() {
        server.stop(1);
        LOGGER.info("server stopped");
    }
}
