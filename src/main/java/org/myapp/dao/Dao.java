package org.myapp.dao;

import org.myapp.entity.BaseEntity;

import java.util.List;

public sealed interface Dao<T extends BaseEntity> permits CatDao, OwnerDao {
    long create(final T t);

    List<T> search();

    T searchById(final long id);

    long update(final T t);

    boolean delete(final long id);
}
