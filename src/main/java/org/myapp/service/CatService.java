package org.myapp.service;

import org.myapp.dao.CatDao;
import org.myapp.dao.Dao;
import org.myapp.dao.OwnerDao;
import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.util.ArrayList;
import java.util.List;

public final class CatService implements Service {
    private final Dao<Cat> catDao;
    private final Dao<Owner> ownerDao;

    public CatService() {
        catDao = new CatDao();
        ownerDao = new OwnerDao();
    }

    @Override
    public long create(final CreateDto createDto) {
        //TODO: validate CreateDto

        long ownerId = ownerDao.create(new Owner.Builder()
                .setName(createDto.getOwnerName())
                .setAge(createDto.getOwnerAge())
                .build());

        long catId = catDao.create(new Cat.Builder()
                .setName(createDto.getName())
                .setColor(createDto.getColor())
                .setWeight(createDto.getWeight())
                .setHeight(createDto.getHeight())
                .setOwnerId(ownerId)
                .build());

        return catId;
    }

    @Override
    public List<SearchDto> search() {
        List<Cat> cats = catDao.search();
        List<SearchDto> searchDtos = new ArrayList<>();
        for (Cat cat : cats) {
            Owner owner = ownerDao.searchById(cat.getOwnerId());
            searchDtos.add(new SearchDto.Builder()
                    .setId(cat.getId())
                    .setName(cat.getName())
                    .setColor(cat.getColor())
                    .setWeight(cat.getWeight())
                    .setHeight(cat.getHeight())
                    .setOwnerId(cat.getOwnerId())
                    .setOwnerName(owner.getName())
                    .setOwnerAge(owner.getAge())
                    .setAnimalsAmount(owner.getAnimalsAmount())
                    .build());
        }
        return searchDtos;
    }

    @Override
    public long update(UpdateDto updateDto) {
        //валидэйтим дто

        long catId = updateDto.getId();
        Cat cat = catDao.searchById(catId);
        System.out.printf("нашли кота по айди %s%n",cat.toString());

        long ownerId = cat.getOwnerId();
        Owner owner = ownerDao.searchById(ownerId);
        System.out.printf("нашли владельца по айди %s%n",owner.toString());

        String ownerName = owner.getName();
        System.out.printf("имя владельца %s%n", ownerName);

        String updatedOwnerName = updateDto.getOwnerName();
        System.out.printf("имя нового владельца %s%n", updatedOwnerName);

        int ownerAge = owner.getAge();
        System.out.printf("возраст владельца %d%n", ownerAge);

        int updatedOwnerAge = updateDto.getOwnerAge();
        System.out.printf("возраст нового владельца %d%n", updatedOwnerAge);


        System.out.printf("айди старого владельца %d%n", ownerId);

        long newOwnerId=ownerId;
        if (!(ownerName.equals(updatedOwnerName)) ||
                ownerAge > updatedOwnerAge) {
            newOwnerId = ownerDao.create(new Owner.Builder()
                    .setName(updatedOwnerName)
                    .setAge(updatedOwnerAge)
                    .build());
            System.out.printf("айди нового владельца %d%n", newOwnerId);

        }
        if (ownerName.equals(updatedOwnerName) &&
                ownerAge < updatedOwnerAge) {
            newOwnerId = ownerDao.update(new Owner.Builder()
                            .setId(ownerId)
                    .setAge(updatedOwnerAge)
                    .build());
            System.out.printf("айди старого владельца но изменился возраст %d%n", newOwnerId);

        }

        long newCatId = catDao.update(new Cat.Builder()
                        .setId(catId)
                .setName(updateDto.getName())
                .setWeight(updateDto.getWeight())
                .setHeight(updateDto.getHeight())
                .setOwnerId(newOwnerId)
                .build());
        System.out.printf("айди обновленного кота %d%n", newCatId);


        return newCatId;
    }

    @Override
    public boolean delete(long id) {
        //validate

        boolean isDeleted = catDao.delete(id);
        return isDeleted;
    }
}
