package org.myapp.service;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;

import java.util.List;

public interface Service {
    void create(CreateDto createDto);

    List<SearchDto> search();

    void update(UpdateDto updateDto);

    void delete(long id);
}
