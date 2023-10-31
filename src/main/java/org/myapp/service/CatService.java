package org.myapp.service;

import org.myapp.dao.CatDao;
import org.myapp.dao.Dao;
import org.myapp.dao.OwnerDao;
import org.myapp.dto.CatDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.util.ArrayList;
import java.util.List;

public final class CatService implements Service<CatDto> {
    private final Dao<Cat> catDao;
    private final Dao<Owner> ownerDao;

    public CatService() {
        catDao = new CatDao();
        ownerDao = new OwnerDao();
    }

    @Override
    public long create(final CatDto catDto) {
        //TODO: validate

        Owner owner = ownerFrom(catDto);
        long ownerId = ownerDao.create(owner);

        Cat cat = catFrom(catDto, ownerId);
        long catId = catDao.create(cat);

        return catId;
    }

    @Override
    public List<CatDto> search() {
        List<Cat> cats = catDao.search();
        List<CatDto> catDtos = new ArrayList<>();
        for (Cat cat : cats) {
            Owner owner = ownerDao.searchById(cat.getOwnerId());
            CatDto catDto = catDtoFrom(cat, owner);
            catDtos.add(catDto);
        }
        return catDtos;
    }

    @Override
    public long update(CatDto catDto) {
        //валидэйтим дто

        long catId = catDto.getId();
        Cat cat = catDao.searchById(catId);
        System.out.printf("нашли кота по айди %s%n", cat.toString());

        long ownerId = cat.getOwnerId();
        Owner owner = ownerDao.searchById(ownerId);
        System.out.printf("нашли владельца по айди %s%n", owner.toString());

        String ownerName = owner.getName();
        System.out.printf("имя владельца %s%n", ownerName);

        String updatedOwnerName = catDto.getOwnerName();
        System.out.printf("имя нового владельца %s%n", updatedOwnerName);

        int ownerAge = owner.getAge();
        System.out.printf("возраст владельца %d%n", ownerAge);

        int updatedOwnerAge = catDto.getOwnerAge();
        System.out.printf("возраст нового владельца %d%n", updatedOwnerAge);

        System.out.printf("айди старого владельца %d%n", ownerId);

        long newOwnerId = ownerId;
        if (!(ownerName.equals(updatedOwnerName)) ||
                ownerAge > updatedOwnerAge) {

            Owner newOwner = ownerFrom(catDto);
            newOwnerId = ownerDao.create(newOwner);
            System.out.printf("айди нового владельца %d%n", newOwnerId);
        }

        if (ownerName.equals(updatedOwnerName) &&
                ownerAge < updatedOwnerAge) {

            Owner newOwner = ownerFrom(catDto, ownerId);
            newOwnerId = ownerDao.update(newOwner);
            System.out.printf("айди старого владельца но изменился возраст %d%n", newOwnerId);
        }

        Cat updatedCat = catFrom(catDto, newOwnerId);
        long newCatId = catDao.update(updatedCat);
        System.out.printf("айди обновленного кота %d%n", newCatId);

        return newCatId;
    }

    @Override
    public boolean delete(long id) {
        //validate

        boolean isDeleted = catDao.delete(id);
        return isDeleted;
    }

    private static Owner ownerFrom(CatDto catDto) {
        return new Owner.Builder()
                .setId(catDto.getOwnerId())
                .setName(catDto.getOwnerName())
                .setAge(catDto.getOwnerAge())
                .build();
    }

    private static Owner ownerFrom(CatDto catDto, long ownerId) {
        return new Owner.Builder()
                .setId(ownerId)
                .setAge(catDto.getOwnerAge())
                .build();
    }

    private static Cat catFrom(CatDto catDto, long ownerId) {
        return new Cat.Builder()
                .setId(catDto.getId())
                .setName(catDto.getName())
                .setColor(catDto.getColor())
                .setWeight(catDto.getWeight())
                .setHeight(catDto.getHeight())
                .setOwnerId(ownerId)
                .build();
    }

    private static CatDto catDtoFrom(Cat cat, Owner owner) {
        return new CatDto.Builder()
                .setId(cat.getId())
                .setName(cat.getName())
                .setColor(cat.getColor())
                .setWeight(cat.getWeight())
                .setHeight(cat.getHeight())
                .setOwnerId(cat.getOwnerId())
                .setOwnerName(owner.getName())
                .setOwnerAge(owner.getAge())
                .setAnimalsAmount(owner.getAnimalsAmount())
                .build();
    }
}
