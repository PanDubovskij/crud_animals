package org.myapp.service;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
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
    public long create(CreateDto createDto) {
        //validate

        //map
        Cat cat = Mapper.createDtoToEntity(createDto);

        cats.add(cat);
        return cat.getId();
    }

    @Override
    public List<SearchDto> search() {
        return cats.stream().map(Mapper::entityToSearchDto).collect(Collectors.toList());
    }

    @Override
    public void update(UpdateDto updateDto) {
        //validate

        //map
        Cat cat = Mapper.updateDtoToEntity(updateDto);
        cats.set((int) (updateDto.getId()), cat);
    }

    @Override
    public void delete(long id) {
        //validate

        cats.remove((int) id);
    }
}
