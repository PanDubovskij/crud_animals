package org.myapp.service;

import org.myapp.dto.Dto;
import org.myapp.entity.Cat;
import org.myapp.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CatService implements Service {
    private final List<Cat> cats;

    public CatService() {
        this.cats = new ArrayList<>();
    }

    @Override
    public void create(Dto dto) {
        //validate

        //map
        Cat cat = Mapper.dtoToEntity(dto);

        cats.add(cat);
    }

    @Override
    public List<Dto> search() {
        return cats.stream().map(Mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void update(Dto dto) {
        //validate

        //map
        Cat cat = Mapper.dtoToEntity(dto);
        cats.set((int) (dto.getId()), cat);
    }

    @Override
    public void delete(long id) {
        //validate

        cats.remove((int) id);
    }
}
