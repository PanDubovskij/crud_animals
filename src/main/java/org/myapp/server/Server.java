package org.myapp.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {
    private HttpServer server;
    private final Map<String, HttpHandler> handlers = new HashMap<>();

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(Map<String, HttpHandler> handlers) {
        this.handlers.putAll(handlers);
    }

    public void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            handlers.forEach((path, hand) -> server.createContext(path, hand));
            server.start();
            logger.info("server started");
        } catch (IOException e) {
            logger.log(Level.WARNING, "some issues with server", e);
        }
    }

    public void stop() {
        server.stop(1);
        logger.info("server stopped");
    }
}
