package org.myapp.utils;

import org.myapp.dto.Dto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

public final class Mapper {
    public static Dto entityToDto(Cat cat) {
        return new Dto.Builder()
                .setId(cat.getId())
                .setName(cat.getName())
                .setColor(cat.getColor())
                .setWeight(cat.getWeight())
                .setHeight(cat.getHeight())
                .setOwnerName(cat.getOwner().getName())
                .setOwnerAge(cat.getOwner().getAge())
                .setAnimalsNumber(cat.getOwner().getAnimalsAmount())
                .build();
    }

    public static Cat dtoToEntity(Dto dto) {

        return new Cat.Builder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setColor(dto.getColor())
                .setWeight(dto.getWeight())
                .setHeight(dto.getHeight())
                .setOwner(new Owner.Builder()
                        .setName(dto.getOwnerName())
                        .setAge(dto.getOwnerAge())
                        .setAnimalsAmount(dto.getAnimalsNumber())
                        .build())
                .build();
    }
}
