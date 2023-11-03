package org.myapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Validator<T> {

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

    public boolean isEmpty() {
        return messages.isEmpty();
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
