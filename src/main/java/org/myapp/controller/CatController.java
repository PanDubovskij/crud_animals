package org.myapp.controller;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.myapp.constants.Attributes;
import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.Owner;
import org.myapp.server.Controller;
import org.myapp.service.Service;

import java.net.URI;
import java.util.*;

public final class CatController extends Controller {

    private final Service service;

    public CatController(final Service service) {
        this.service = service;
        System.out.println("catController");
    }

    @Override
    public long create(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        CreateDto createDto = new CreateDto.Builder()
                .setName(jsonObject.getString(Attributes.NAME))
                .setColor(jsonObject.getString(Attributes.COLOR))
                .setHeight(Integer.parseInt(jsonObject.getString(Attributes.HEIGHT)))
                .setWeight(Integer.parseInt(jsonObject.getString(Attributes.WEIGHT)))
                .setOwner(new Owner.Builder().build())
                .build();

        long id = service.create(createDto);
        System.out.println("create in controller");
        return id;
    }

    @Override
    public List<JsonObject> search(HttpExchange httpExchange) {
//        URI requestURI = httpExchange.getRequestURI();
//        Map<Attributes, String> attributes = new HashMap<>();
//        searchAttributeUrl(requestURI, attributes);
        List<SearchDto> cats = service.search();

        List<JsonObject> jsonObjects = new ArrayList<>();
        for (SearchDto cat : cats) {
            JsonObject json = new JsonObject();
            json.put(Attributes.NAME, cat.getName());
            json.put(Attributes.COLOR, cat.getColor());
            json.put(Attributes.WEIGHT, cat.getWeight());
            json.put(Attributes.HEIGHT, cat.getHeight());
            json.put(Attributes.OWNER_NAME, cat.getOwner().getName());
            json.put(Attributes.OWNER_AGE, cat.getOwner().getAge());
            json.put(Attributes.ANIMALS_NUMBER, cat.getOwner().getAnimalsAmount());
            jsonObjects.add(json);
        }
        System.out.println("search in controller");

        return jsonObjects;
    }

    @Override
    public long update(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        UpdateDto updateDto = new UpdateDto.Builder()
                .setId(jsonObject.getLong(Attributes.ID))
                .setName(jsonObject.getString(Attributes.NAME))
                .setHeight(jsonObject.getInteger(Attributes.HEIGHT))
                .setWeight(jsonObject.getInteger(Attributes.WEIGHT))
                .setOwner(new Owner.Builder()
                        .setName(jsonObject.getString(Attributes.OWNER_NAME))
                        .setAge(jsonObject.getInteger(Attributes.OWNER_AGE))
                        .build())
                .build();
        long id = service.update(updateDto);
        System.out.println("update in controller");
        return id;
    }

    @Override
    public boolean delete(HttpExchange httpExchange) {
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
