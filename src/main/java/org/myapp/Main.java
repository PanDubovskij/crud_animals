package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.constants.Constants;
import org.myapp.controller.CatController;
import org.myapp.dao.CatDao;
import org.myapp.dao.Dao;
import org.myapp.dao.OwnerDao;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;
import org.myapp.server.Handler;
import org.myapp.server.Server;
import org.myapp.service.CatService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dao<Owner> ownerDao = new OwnerDao();
        Dao<Cat> catDao = new CatDao();

        Owner owner1 = new Owner.Builder()
                .setName("jan")
                .setAge(20)
                .build();
        Owner owner2 = new Owner.Builder()
                .setName("olga")
                .setAge(19)
                .build();

        for (Owner owner : ownerDao.search()) {
            System.out.println(owner.toString());
        }
        System.out.println();

        for (Owner owner : ownerDao.search()) {
            System.out.println(owner.toString());
        }

        Cat cat1 = new Cat.Builder()
                .setName("rizhij")
                .setColor("red")
                .setWeight(5)
                .setHeight(30)
                .setOwner(owner1)
                .build();

        Cat cat2 = new Cat.Builder()
                .setName("varia")
                .setColor("black")
                .setWeight(6)
                .setHeight(25)
                .setOwner(owner1)
                .build();

        Cat cat3 = new Cat.Builder()
                .setName("nusha")
                .setColor("red-white")
                .setWeight(1)
                .setHeight(10)
                .setOwner(owner2)
                .build();

        catDao.create(cat1);


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