package org.myapp.service;

import org.myapp.dao.Dao;
import org.myapp.dto.CatDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.myapp.util.Constants.INVALID_ID;

public final class CatService implements Service<CatDto> {
    private final Dao<Cat> catDao;
    private final Dao<Owner> ownerDao;

    public CatService(Dao<Cat> catDao, Dao<Owner> ownerDao) {
        this.catDao = catDao;
        this.ownerDao = ownerDao;
    }

    @Override
    public long create(final CatDto catDto) {
        Owner owner = ownerFrom(catDto);
        long ownerId = ownerDao.create(owner);
        Cat cat = catFrom(catDto, ownerId);
        return catDao.create(cat);
    }

    @Override
    public List<CatDto> search() {
        List<CatDto> catDtos = new ArrayList<>();

        List<Cat> cats = catDao.search();
        for (Cat cat : cats) {
            Optional<Owner> owner = ownerDao.searchBy(cat.getOwnerId());
            if (owner.isPresent()) {
                CatDto catDto = catDtoFrom(cat, owner.get());
                catDtos.add(catDto);
            }
        }
        return catDtos;
    }

    @Override
    public long update(final CatDto catDto) {
        long newCatId = INVALID_ID;

        long catId = catDto.getId();
        Optional<Cat> cat = catDao.searchBy(catId);

        if (cat.isPresent()) {
            long ownerId = cat.get().getOwnerId();
            Optional<Owner> owner = ownerDao.searchBy(ownerId);

            if (owner.isPresent()) {
                String ownerName = owner.get().getName();
                String updatedOwnerName = catDto.getOwnerName();

                int ownerAge = owner.get().getAge();
                int updatedOwnerAge = catDto.getOwnerAge();

                long newOwnerId = ownerId;
                if (!(ownerName.equals(updatedOwnerName)) ||
                        ownerAge > updatedOwnerAge) {

                    Owner newOwner = ownerFrom(catDto);
                    newOwnerId = ownerDao.create(newOwner);
                }

                if (ownerName.equals(updatedOwnerName) &&
                        ownerAge < updatedOwnerAge) {

                    Owner newOwner = ownerFrom(catDto, ownerId);
                    newOwnerId = ownerDao.update(newOwner);
                }

                Cat updatedCat = catFrom(catDto, newOwnerId);
                newCatId = catDao.update(updatedCat);
            }
        }
        return newCatId;
    }

    @Override
    public boolean delete(final long id) {
        return catDao.delete(id);
    }

    private Owner ownerFrom(final CatDto catDto) {
        return new Owner.Builder()
                .setId(catDto.getOwnerId())
                .setName(catDto.getOwnerName())
                .setAge(catDto.getOwnerAge())
                .build();
    }

    private Owner ownerFrom(final CatDto catDto, final long ownerId) {
        return new Owner.Builder()
                .setId(ownerId)
                .setAge(catDto.getOwnerAge())
                .build();
    }

    private Cat catFrom(final CatDto catDto, final long ownerId) {
        return new Cat.Builder()
                .setId(catDto.getId())
                .setName(catDto.getName())
                .setColor(catDto.getColor())
                .setWeight(catDto.getWeight())
                .setHeight(catDto.getHeight())
                .setOwnerId(ownerId)
                .build();
    }

    private CatDto catDtoFrom(final Cat cat, final Owner owner) {
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
