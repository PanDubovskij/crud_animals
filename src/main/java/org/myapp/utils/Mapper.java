package org.myapp.utils;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

public final class Mapper {
    public static SearchDto entityToSearchDto(Cat cat) {

        return new SearchDto.Builder()
                .setId(cat.getId())
                .setName(cat.getName())
                .setColor(cat.getColor())
                .setWeight(cat.getWeight())
                .setHeight(cat.getHeight())
                .setOwnerId(cat.getOwnerId())
//                .setOwnerName(cat.get)
//                .setOwnerAge(cat.getOwner().getAge())
//                .setAnimalsNumber(cat.getOwner().getAnimalsAmount())
                .build();
    }

    public static Cat createDtoToEntity(CreateDto createDto) {

        return new Cat.Builder()
                .setName(createDto.getName())
                .setColor(createDto.getColor())
                .setWeight(createDto.getWeight())
                .setHeight(createDto.getHeight())
                .build();
    }

    public static Cat updateDtoToEntity(UpdateDto updateDto) {

        return new Cat.Builder()
                .setId(updateDto.getId())
                .setName(updateDto.getName())
                .setWeight(updateDto.getWeight())
                .setHeight(updateDto.getHeight())
                .build();
    }
}
