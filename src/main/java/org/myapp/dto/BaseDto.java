package org.myapp.dto;

public abstract sealed class BaseDto permits CatDto, OwnerDto {
}
