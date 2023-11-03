package org.myapp.controller;

import org.myapp.dao.CatDao;
import org.myapp.dao.OwnerDao;
import org.myapp.service.CatService;

public class ControllerFactory {

////    public static Controller newResistorController() {
//        return new CatController(new CatService(new CatDao()));
////    }

//    public static Controller flexibleCapacitorController(Map<String, String> properties) {
//        return new ControllerCapacitor(new ServiceCapacitor(new CapacitorDao.Builder().type(FLEXIBLE).property(properties).build()));
//}

    public static Controller newCatController() {
        return new CatController(new CatService(new CatDao(), new OwnerDao()));
    }

//    public static Controller newDogController() {
//        return new DogController(new DogService(new DogDao(), new OwnerDao()));
//    }

}
