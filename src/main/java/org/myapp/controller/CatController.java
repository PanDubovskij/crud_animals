package org.myapp.controller;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.myapp.constants.Attributes;
import org.myapp.dto.CatDto;
import org.myapp.server.Controller;
import org.myapp.service.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CatController extends Controller {

    private final Service<CatDto> service;

    public CatController(final Service<CatDto> service) {
        this.service = service;
        System.out.println("catController");
    }

    @Override
    protected long create(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        CatDto catDto = new CatDto.Builder()
                .setName(jsonObject.getString(Attributes.NAME))
                .setColor(jsonObject.getString(Attributes.COLOR))
                .setWeight(Integer.parseInt(jsonObject.getString(Attributes.WEIGHT)))
                .setHeight(Integer.parseInt(jsonObject.getString(Attributes.HEIGHT)))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(Integer.parseInt(jsonObject.getString(Attributes.OWNER_AGE)))
                .build();

        long id = service.create(catDto);
        System.out.println("create in controller");
        return id;
    }

    @Override
    protected List<JsonObject> search(HttpExchange httpExchange) {
//        URI requestURI = httpExchange.getRequestURI();
//        Map<Attributes, String> attributes = new HashMap<>();
//        searchAttributeUrl(requestURI, attributes);
        List<CatDto> cats = service.search();

        List<JsonObject> jsonObjects = new ArrayList<>();
        for (CatDto cat : cats) {
            JsonObject json = new JsonObject();
            json.put(Attributes.NAME, cat.getName());
            json.put(Attributes.COLOR, cat.getColor());
            json.put(Attributes.WEIGHT, cat.getWeight());
            json.put(Attributes.HEIGHT, cat.getHeight());
            json.put(Attributes.OWNER_NAME, cat.getOwnerName());
            json.put(Attributes.OWNER_AGE, cat.getOwnerAge());
            json.put(Attributes.ANIMALS_NUMBER, cat.getAnimalsAmount());
            jsonObjects.add(json);
        }
        System.out.println("search in controller");

        return jsonObjects;
    }

    @Override
    protected long update(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        CatDto catDto = new CatDto.Builder()
                .setId(jsonObject.getLong(Attributes.ID))
                .setName(jsonObject.getString(Attributes.NAME))
                .setWeight(jsonObject.getInteger(Attributes.WEIGHT))
                .setHeight(jsonObject.getInteger(Attributes.HEIGHT))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(jsonObject.getInteger(Attributes.OWNER_AGE))
                .build();
        long id = service.update(catDto);
        System.out.println("update in controller");
        return id;
    }

    @Override
    protected boolean delete(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        Optional<String> id = readAttributes(uri, Attributes.ID);
        boolean result = false;
        if (id.isPresent()) {
            result = service.delete(Long.parseLong(id.get()));
        }
        System.out.println("delete in controller");
        return result;
    }
}
