package org.myapp.dao;

import org.myapp.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Provide CRUD-interface to work with entity.
 *
 * @param <T> Dao will work with provided class extended from {@link BaseEntity}.
 */
public sealed interface Dao<T extends BaseEntity> permits CatDao, OwnerDao {

    /**
     * @param   t entity need to be created.
     * @return  id of created entity, if something went wrong return -1.
     */
    long create(final T t);

    /**
     * Search all entities.
     *
     * @return List of all exciting entities.
     */
    List<T> search();

    /**
     * Search entity by id
     *
     * @param id of required entity
     * @return  {@link Optional} with entity if there's entity with given id, otherwise return empty Optional
     */
    Optional<T> searchBy(final long id);

    /**
     * Update entity with given id
     *
     * @param t new entity need to be replaced with old one
     * @return  return id of updated entity, if something went wrong return -1
     */
    long update(final T t);

    /**
     * Delete entity by id.
     *
     * @param id provided id of entity to be deleted
     * @return  return true if entity was deleted, otherwise false
     */
    boolean delete(final long id);
}
