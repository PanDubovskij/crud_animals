package org.myapp.service;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;
import org.myapp.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CatService implements Service {

    private final List<Cat> cats;
//    private final List<Owner> owners;

    public CatService() {
        this.cats = new ArrayList<>();
//        this.owners = new ArrayList<>();
        System.out.println("CatService");
    }

    @Override
    public long create(CreateDto createDto) {
        //TODO: validate CreateDto
        //Todo: map to Cat
        //TODO: ownerDao.createOwnerIfNotExist(Owner): return ownerID
        //TODO: add ownerId to Cat
        //TODO: catDao.createCat(Cat): return catID












        //validate

        //map
        Cat cat = Mapper.createDtoToEntity(createDto);

        cats.add(cat);
//        owners.add(cat.getOwner());
        System.out.println("service create");
        return cat.getId();
    }

    @Override
    public List<SearchDto> search() {
        List<SearchDto> list = cats.stream().map(Mapper::entityToSearchDto).collect(Collectors.toList());
        System.out.println("service search");
        return list;
    }

    @Override
    public long update(UpdateDto updateDto) {
        //validate

        //map
        Cat cat = Mapper.updateDtoToEntity(updateDto);
        cats.set((int) (cat.getId()), cat);
        long id = cat.getId();
        System.out.println("service update");
        return id;
    }

    @Override
    public boolean delete(long id) {
        //validate

        Optional<Cat> o = Optional.of(cats.remove((int) id));
        System.out.println("service delete");
        return o.isPresent();
    }
}
