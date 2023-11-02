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
    protected JsonObject create(final HttpExchange httpExchange) {
        JsonObject requestJson = readRequestFromJson(httpExchange);
        CatDto catDto = createCatDtoFrom(requestJson);

        long id = service.create(catDto);

        JsonObject responseJson = new JsonObject();
        responseJson.put(Attributes.ID, id);
        System.out.println("create in controller");

        return responseJson;
    }

    @Override
    protected List<JsonObject> search(final HttpExchange httpExchange) {
//        URI requestURI = httpExchange.getRequestURI();
//        Map<Attributes, String> attributes = new HashMap<>();
//        searchAttributeUrl(requestURI, attributes);
        List<CatDto> catDtos = service.search();

        List<JsonObject> jsonObjects = new ArrayList<>();
        for (CatDto catDto : catDtos) {
            JsonObject json = jsonFrom(catDto);
            jsonObjects.add(json);
        }
        System.out.println("search in controller");

        return jsonObjects;
    }

    @Override
    protected JsonObject update(final HttpExchange httpExchange) {
        JsonObject requestJson = readRequestFromJson(httpExchange);
        CatDto catDto = updateCatDtoFrom(requestJson);

        long id = service.update(catDto);
        JsonObject responseJson = new JsonObject();
        responseJson.put(Attributes.ID, id);
        System.out.println("update in controller");

        return responseJson;
    }

    @Override
    protected boolean delete(final HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        Optional<String> id = readAttributes(uri, Attributes.ID);
        boolean result = false;
        if (id.isPresent()) {
            result = service.delete(id.get());
        }
        System.out.println("delete in controller");
        return result;
    }

    private CatDto createCatDtoFrom(final JsonObject jsonObject) {
        return new CatDto.Builder()
                .setName(jsonObject.getString(Attributes.NAME))
                .setColor(jsonObject.getString(Attributes.COLOR))
                .setWeight(jsonObject.getInteger(Attributes.WEIGHT))
                .setHeight(jsonObject.getInteger(Attributes.HEIGHT))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(jsonObject.getInteger(Attributes.OWNER_AGE))
                .build();
    }

    private CatDto updateCatDtoFrom(final JsonObject jsonObject) {
        return new CatDto.Builder()
                .setId(jsonObject.getLong(Attributes.ID))
                .setName(jsonObject.getString(Attributes.NAME))
                .setWeight(jsonObject.getInteger(Attributes.WEIGHT))
                .setHeight(jsonObject.getInteger(Attributes.HEIGHT))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(jsonObject.getInteger(Attributes.OWNER_AGE))
                .build();
    }

    private JsonObject jsonFrom(final CatDto catDto) {
        JsonObject json = new JsonObject();
        json.put(Attributes.NAME, catDto.getName());
        json.put(Attributes.COLOR, catDto.getColor());
        json.put(Attributes.WEIGHT, catDto.getWeight());
        json.put(Attributes.HEIGHT, catDto.getHeight());
        json.put(Attributes.OWNER_NAME, catDto.getOwnerName());
        json.put(Attributes.OWNER_AGE, catDto.getOwnerAge());
        json.put(Attributes.ANIMALS_NUMBER, catDto.getAnimalsAmount());
        return json;
    }
}
