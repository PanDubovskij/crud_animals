package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.constants.Constants;
import org.myapp.controller.CatController;
import org.myapp.server.Handler;
import org.myapp.server.Server;
import org.myapp.service.CatService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


//        Map<String, HttpHandler> handlers = new HashMap<>();
//        handlers.put(Constants.CATS_PATH, new Handler(new CatController(new CatService())));
//        Server server = new Server(handlers);
//        server.start();
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()) {
//            if ("q".equalsIgnoreCase(scanner.next())) {
//                System.out.print("Termination of the program...");
//                server.stop();
//                return;
//            } else {
//                System.out.print("To terminate the program press: q/Q: ");
//            }
//        }
    }
}