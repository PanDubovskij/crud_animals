package org.myapp.service;

import org.myapp.dto.Dto;

import java.util.List;

public interface Service {
    void create(Dto dto);

    List<Dto> search();

    void update(Dto dto);

    void delete(long id);
}
