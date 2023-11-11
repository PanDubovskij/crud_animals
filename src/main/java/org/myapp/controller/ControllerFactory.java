package org.myapp.controller;

import org.myapp.dao.CatDao;
import org.myapp.dao.OwnerDao;
import org.myapp.service.CatService;

import java.util.Map;

/**
 * Provides factory methods for creating controllers
 */
public final class ControllerFactory {
    /**
     * @return CatController with default configuration
     */
    public static Controller newCatController() {
        return new CatController(new CatService(new CatDao(), new OwnerDao()));
    }
    /**
     * @return custom CatController with given properties
     */
    public static Controller newCatController(Map<String, String> properties) {
        return new CatController(new CatService(new CatDao(properties), new OwnerDao(properties)));
    }

}
