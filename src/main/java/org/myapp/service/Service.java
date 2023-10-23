package org.myapp.service;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;

import java.util.List;

public interface Service {
    long create(CreateDto createDto);

    List<SearchDto> search();

    long update(UpdateDto updateDto);

    boolean delete(long id);
}
