package org.myapp.dao;

import org.myapp.dto.CreateDto;
import org.myapp.dto.SearchDto;
import org.myapp.dto.UpdateDto;
import org.myapp.entity.BaseEntity;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.util.List;

public interface Dao<T extends BaseEntity> {
    long create(T t);

    List<T> search();

    long update(T t);

    boolean delete(long id);
}
