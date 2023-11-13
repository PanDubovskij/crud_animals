package org.myapp.service;

import org.myapp.dto.BaseDto;

import java.util.List;

/**
 * Provide CRUD-interface to work with dto.
 *
 * @param <T> Service will work with provided class extended from {@link BaseDto}.
 */
public sealed interface Service<T extends BaseDto> permits CatService {
    /**
     * @param t dto
     * @return id of created instance.
     */
    long create(final T t);

    /**
     * @return list of dtos.
     */
    List<T> search();

    /**
     * @param t dto
     * @return id of updated instance
     */
    long update(final T t);

    /**
     * @param id of removable dto
     * @return true if instance was deleted otherwise false
     */
    boolean delete(final long id);
}
