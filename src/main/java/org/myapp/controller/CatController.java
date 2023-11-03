package org.myapp.controller;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.myapp.dto.CatDto;
import org.myapp.service.Service;
import org.myapp.util.Validator;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static org.myapp.controller.Constants.REG_DIGIT;
import static org.myapp.controller.Constants.REG_NOT_BLANK;
import static org.myapp.util.Constants.INVALID_ID;

public final class CatController extends Controller {

    private final Service<CatDto> service;

    public CatController(final Service<CatDto> service) {
        this.service = service;
    }

    @Override
    protected JsonObject create(final HttpExchange httpExchange) {
        long id = INVALID_ID;

        Optional<JsonObject> optionalRequestJson = readRequestFromJson(httpExchange);
        if (optionalRequestJson.isPresent()) {
            JsonObject requestJson = optionalRequestJson.get();

            logger.info(requestJson.getString(Attributes.NAME));

            Validator<JsonObject> validator = Validator.of(requestJson)
                    .validator(c -> c.getString(Attributes.NAME).matches(REG_NOT_BLANK), "invalid name")
                    .validator(c -> c.getString(Attributes.COLOR).matches(REG_NOT_BLANK), "invalid color")
                    .validator(c -> c.getInteger(Attributes.WEIGHT).toString().matches(REG_DIGIT), "invalid weight")
                    .validator(c -> c.getInteger(Attributes.HEIGHT).toString().matches(REG_DIGIT), "invalid height")
                    .validator(c -> c.getString(Attributes.OWNER_NAME).matches(REG_NOT_BLANK), "invalid owner name")
                    .validator(c -> c.getInteger(Attributes.OWNER_AGE).toString().matches(REG_DIGIT), "invalid owner age");
            try {
                requestJson = validator.get();
                CatDto catDto = createdCatDtoFrom(requestJson);
                id = service.create(catDto);
            } catch (IllegalStateException e) {
                logger.log(Level.WARNING, "invalid json", e);
            }
        } else {
            logger.warning("no json");
        }
        JsonObject responseJson = new JsonObject();
        responseJson.put(Attributes.ID, id);

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

        return jsonObjects;
    }

    @Override
    protected JsonObject update(final HttpExchange httpExchange) {
        long id = INVALID_ID;

        Optional<JsonObject> optionalRequestJson = readRequestFromJson(httpExchange);
        if (optionalRequestJson.isPresent()) {
            JsonObject requestJson = optionalRequestJson.get();
            Validator<JsonObject> validator = Validator.of(requestJson)
                    .validator(c -> c.getLong(Attributes.ID).toString().matches(REG_DIGIT), "invalid id")
                    .validator(c -> c.getString(Attributes.NAME).matches(REG_NOT_BLANK), "invalid name")
                    .validator(c -> c.getInteger(Attributes.WEIGHT).toString().matches(REG_DIGIT), "invalid weight")
                    .validator(c -> c.getInteger(Attributes.HEIGHT).toString().matches(REG_DIGIT), "invalid height")
                    .validator(c -> c.getString(Attributes.OWNER_NAME).matches(REG_NOT_BLANK), "invalid owner name")
                    .validator(c -> c.getInteger(Attributes.OWNER_AGE).toString().matches(REG_DIGIT), "invalid owner age");
            try {
                requestJson = validator.get();
                CatDto catDto = updatedCatDtoFrom(requestJson);
                id = service.update(catDto);
            } catch (IllegalStateException e) {
                logger.log(Level.WARNING, "invalid json", e);
            }
        } else {
            logger.warning("no json");
        }
        JsonObject responseJson = new JsonObject();
        responseJson.put(Attributes.ID, id);

        return responseJson;
    }

    @Override
    protected boolean delete(final HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        Optional<String> optionalId = readAttributes(uri, Attributes.ID);
        boolean result = false;

        if (optionalId.isPresent()) {
            String id = optionalId.get();
            Validator<String> validatorId = Validator.of(id)
                    .validator(i -> i.matches(REG_DIGIT), "id is not a number");
            try {
                id = validatorId.get();
                result = service.delete(Long.parseLong(id));
            } catch (IllegalStateException ignored) {
            }
        }
        return result;
    }

    private CatDto createdCatDtoFrom(final JsonObject jsonObject) {
        return new CatDto.Builder()
                .setName(jsonObject.getString(Attributes.NAME))
                .setColor(jsonObject.getString(Attributes.COLOR))
                .setWeight(jsonObject.getInteger(Attributes.WEIGHT))
                .setHeight(jsonObject.getInteger(Attributes.HEIGHT))
                .setOwnerName(jsonObject.getString(Attributes.OWNER_NAME))
                .setOwnerAge(jsonObject.getInteger(Attributes.OWNER_AGE))
                .build();
    }

    private CatDto updatedCatDtoFrom(final JsonObject jsonObject) {
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
