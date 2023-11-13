package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.connection.ConnectionPool;
import org.myapp.controller.ControllerFactory;
import org.myapp.controller.Handler;
import org.myapp.server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.myapp.util.Constants.CATS_PATH;

final class Main {
    public static void main(String[] args) {
        Map<String, HttpHandler> handlers = new HashMap<>();
        handlers.put(CATS_PATH, new Handler(ControllerFactory.newCatController()));
        Server server = new Server(handlers);
        server.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            if ("q".equalsIgnoreCase(scanner.next())) {
                System.out.print("Termination of the program...\n");
                server.stop();
                ConnectionPool.INSTANCE.destroyPool();
                return;
            } else {
                System.out.print("To terminate the program press: q/Q: ");
            }
        }
    }
}