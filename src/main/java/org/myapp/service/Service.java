package org.myapp.service;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;

import java.util.List;

public sealed interface Service permits CatService {
    long create(final CreateDto createDto);

    List<SearchDto> search();

    long update(final UpdateDto updateDto);

    boolean delete(final long id);
}
