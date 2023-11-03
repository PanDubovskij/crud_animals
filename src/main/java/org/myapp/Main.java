package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.controller.Constants;
import org.myapp.controller.ControllerFactory;
import org.myapp.controller.Handler;
import org.myapp.server.Server;
import org.myapp.connection.ConnectionPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        CatDto rizhiy = new CatDto.Builder()
//                .setName("rizhiy")
//                .setColor("red")
//                .setWeight(5)
//                .setHeight(30)
//                .setOwnerName("jan")
//                .setOwnerAge(20)
//                .build();
//        CatDto varya = new CatDto.Builder()
//                .setName("varya")
//                .setColor("black-white")
//                .setWeight(6)
//                .setHeight(25)
//                .setOwnerName("jan")
//                .setOwnerAge(20)
//                .build();
//
//        CatDto nusha = new CatDto.Builder()
//                .setName("nusha")
//                .setColor("red-white")
//                .setWeight(1)
//                .setHeight(10)
//                .setOwnerName("olga")
//                .setOwnerAge(20)
//                .build();
//
//        CatDto updatedRizhiy = new CatDto.Builder()
//                .setId(32)
//                .setName("rizhiy")
//                .setWeight(5)
//                .setHeight(30)
//                .setOwnerName("ksusha")
//                .setOwnerAge(43)
//                .build();
//
//        CatDto updatedVarya1 = new CatDto.Builder()
//                .setId(33)
//                .setName("varya")
//                .setWeight(5)
//                .setHeight(25)
//                .setOwnerName("jan")
//                .setOwnerAge(21)
//                .build();
//
//        CatDto updatedNusha = new CatDto.Builder()
//                .setId(34)
//                .setName("nusha")
//                .setWeight(1)
//                .setHeight(15)
//                .setOwnerName("olga")
//                .setOwnerAge(20)
//                .build();

//        CatService catService = new CatService(new CatDao(), new OwnerDao());
//
//
//        System.out.println("must be no cats");
//        for (CatDto s : catService.search()) {
//            System.out.println(s.toString());
//        }
//        System.out.println();
//
////        catService.create(rizhiy);
////        catService.create(varya);
////        catService.create(nusha);
//        System.out.println("cats after create");
//        for (CatDto s : catService.search()) {
//            System.out.println(s.toString());
//        }
//        System.out.println();
//
////        catService.update(updatedVarya1);
////        catService.update(updatedRizhiy);
////        catService.update(updatedNusha);
//        System.out.println("cats after update");
//        for (CatDto s : catService.search()) {
//            System.out.println(s.toString());
//        }
//        System.out.println();

//        catService.delete(7);
//        System.out.println("cats after delete");
//        for (CatDto s : catService.search()) {
//            System.out.println(s.toString());
//        }


        Map<String, HttpHandler> handlers = new HashMap<>();
        handlers.put(Constants.CATS_PATH, new Handler(ControllerFactory.newCatController()));
//        handlers.put(Constants.DOGS_PATH, new Handler(new DogController(new DogService(new DogDao(), new OwnerDao()))));
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