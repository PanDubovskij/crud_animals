package org.myapp.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class Server {
    private HttpServer server;


    private final Map<String, HttpHandler> handlers = new HashMap<>();

    public Server(Map<String, HttpHandler> handlers) {
        this.handlers.putAll(handlers);
        System.out.println("Server");
    }

    public void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            handlers.forEach((path, hand) -> server.createContext(path, hand::handle));
            server.start();
            System.out.println("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
//        ConnectionPool.INSTANCE.destroyPool();
        server.stop(1);
        System.out.println("stop");
    }
}
