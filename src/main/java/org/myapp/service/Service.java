package org.myapp.service;

import org.myapp.dto.BaseDto;
import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.BaseEntity;

import java.util.List;

public sealed interface Service<T extends BaseDto> permits CatService {
    long create(final T t);

    List<T> search();

    long update(final T t);

    boolean delete(final long id);
}
