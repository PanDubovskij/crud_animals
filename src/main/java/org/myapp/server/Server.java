package org.myapp.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private HttpServer server;


    private final Map<String, HttpHandler> handlers = new HashMap<>();

    public Server(Map<String, HttpHandler> handlers) {
        this.handlers.putAll(handlers);
    }

    public void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            handlers.forEach((path, hand) -> server.createContext(path, hand::handle));
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
//        ConnectionPool.INSTANCE.destroyPool();
        server.stop(1);
    }
}
