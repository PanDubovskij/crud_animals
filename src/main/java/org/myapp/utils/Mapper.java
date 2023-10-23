package org.myapp.utils;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

public final class Mapper {
    public static SearchDto entityToSearchDto(Cat cat) {
        System.out.println("entityToSearchDto");
        return new SearchDto.Builder()
                .setName(cat.getName())
                .setColor(cat.getColor())
                .setWeight(cat.getWeight())
                .setHeight(cat.getHeight())
                .setOwnerName(cat.getOwner().getName())
                .setOwnerAge(cat.getOwner().getAge())
                .setAnimalsNumber(cat.getOwner().getAnimalsAmount())
                .build();
    }

    public static Cat createDtoToEntity(CreateDto createDto) {
        System.out.println("createDtoToEntity");

        return new Cat.Builder()
                .setName(createDto.getName())
                .setColor(createDto.getColor())
                .setWeight(createDto.getWeight())
                .setHeight(createDto.getHeight())
                .setOwner(new Owner.Builder()
                        .setName(createDto.getOwnerName())
                        .setAge(createDto.getOwnerAge())
                        .build())
                .build();
    }

    public static Cat updateDtoToEntity(UpdateDto updateDto) {

        System.out.println("updateDtoToEntity");
        return new Cat.Builder()
                .setId(updateDto.getId())
                .setName(updateDto.getName())
                .setWeight(updateDto.getWeight())
                .setHeight(updateDto.getHeight())
                .setOwner(new Owner.Builder()
                        .setName(updateDto.getOwnerName())
                        .setAge(updateDto.getOwnerAge())
                        .build())
                .build();
    }
}
