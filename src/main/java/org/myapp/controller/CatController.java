package org.myapp.controller;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.myapp.constants.Attributes;
import org.myapp.dto.CreateDto;
import org.myapp.server.Controller;
import org.myapp.service.Service;

import java.util.List;

public final class CatController extends Controller {

    private final Service service;

    public CatController(final Service service) {
        this.service = service;
    }
    @Override
    public long create(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        CreateDto createDto = new CreateDto.Builder()
                .setName(jsonObject.getString(Attributes.NAME))
                .setColor(jsonObject.getString(Attributes.COLOR))
                .setHeight(Integer.parseInt(jsonObject.getString(Attributes.HEIGHT)))
                .setWeight(Integer.parseInt(jsonObject.getString(Attributes.WEIGHT)))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(Integer.parseInt(jsonObject.getString(Attributes.OWNER_AGE)))
                .build();
        return service.create(createDto);
    }

    @Override
    public List<JsonObject> search(HttpExchange httpExchange) {
        return null;
    }

    @Override
    public long update(HttpExchange httpExchange) {
        return 0;
    }

    @Override
    public boolean delete(HttpExchange httpExchange) {
        return false;
    }
}
