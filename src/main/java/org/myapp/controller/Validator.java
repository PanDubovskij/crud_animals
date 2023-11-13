package org.myapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Class representing Monad design pattern. Monad is a way of chaining operations on the given
 * object together step by step. In Validator each step results in either success or failure
 * indicator, giving a way of receiving each of them easily and finally getting validated object or
 * list of exceptions.
 *
 * @param <T> Placeholder for an object.
 */
final class Validator<T> {

    private final T object;
    private final List<String> messages = new ArrayList<>();

    private Validator(T object) {
        this.object = object;
    }

    public static <T> Validator<T> of(T object) {
        return new Validator<>(Objects.requireNonNull(object));
    }

    public Validator<T> validator(Predicate<T> predicate, String message) {
        if (!predicate.test(object)) {
            messages.add(message);
        }
        return this;
    }

    public T get() throws IllegalStateException {
        if (messages.isEmpty()) {
            return object;
        }

        StringBuilder sb = new StringBuilder();
        for (String msg : messages) {
            sb.append(msg).append(", ");
        }
        throw new IllegalStateException(sb.toString());
    }
}
