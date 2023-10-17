package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.server.Server;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<String, HttpHandler> handlers = new HashMap<>();
        Server server = new Server(handlers);
        server.start();
        Thread.sleep(5000);
        server.stop();
    }
}