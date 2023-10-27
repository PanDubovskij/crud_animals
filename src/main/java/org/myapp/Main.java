package org.myapp;

import com.sun.net.httpserver.HttpHandler;
import org.myapp.constants.Constants;
import org.myapp.controller.CatController;
import org.myapp.dao.CatDao;
import org.myapp.dao.Dao;
import org.myapp.dao.OwnerDao;
import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
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


        CreateDto rizhiy = new CreateDto.Builder()
                .setName("rizhiy")
                .setColor("red")
                .setWeight(5)
                .setHeight(30)
                .setOwnerName("jan")
                .setOwnerAge(20)
                .build();
        CreateDto varya = new CreateDto.Builder()
                .setName("varya")
                .setColor("black-white")
                .setWeight(6)
                .setHeight(25)
                .setOwnerName("jan")
                .setOwnerAge(20)
                .build();

        CreateDto nusha = new CreateDto.Builder()
                .setName("nusha")
                .setColor("red-white")
                .setWeight(1)
                .setHeight(10)
                .setOwnerName("olga")
                .setOwnerAge(20)
                .build();

        UpdateDto updatedRizhiy = new UpdateDto.Builder()
                .setId(32)
                .setName("rizhiy")
                .setWeight(5)
                .setHeight(30)
                .setOwnerName("ksusha")
                .setOwnerAge(43)
                .build();

        UpdateDto updatedVarya1 = new UpdateDto.Builder()
                .setId(33)
                .setName("varya")
                .setWeight(5)
                .setHeight(25)
                .setOwnerName("jan")
                .setOwnerAge(21)
                .build();

        UpdateDto updatedNusha = new UpdateDto.Builder()
                .setId(34)
                .setName("nusha")
                .setWeight(1)
                .setHeight(15)
                .setOwnerName("olga")
                .setOwnerAge(20)
                .build();

        CatService catService = new CatService();


        System.out.println("must be no cats");
        for (SearchDto s : catService.search()) {
            System.out.println(s.toString());
        }
        System.out.println();

//        catService.create(rizhiy);
//        catService.create(varya);
//        catService.create(nusha);
        System.out.println("cats after create");
        for (SearchDto s : catService.search()) {
            System.out.println(s.toString());
        }
        System.out.println();

//        catService.update(updatedVarya1);
//        catService.update(updatedRizhiy);
//        catService.update(updatedNusha);
        System.out.println("cats after update");
        for (SearchDto s : catService.search()) {
            System.out.println(s.toString());
        }
        System.out.println();

//        catService.delete(7);
//        System.out.println("cats after delete");
//        for (SearchDto s : catService.search()) {
//            System.out.println(s.toString());
//        }


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