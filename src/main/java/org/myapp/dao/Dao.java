package org.myapp.dao;

import org.myapp.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public sealed interface Dao<T extends BaseEntity> permits CatDao, OwnerDao {

    long create(final T t);

    List<T> search();

    Optional<T> searchBy(final long id);

    long update(final T t);

    boolean delete(final long id);
}
