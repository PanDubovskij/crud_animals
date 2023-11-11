package org.myapp.dto;


/**
 * Base class of DTO hierarchy.
 */
public abstract sealed class BaseDto permits CatDto, OwnerDto {
}
