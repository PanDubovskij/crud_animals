package org.myapp.constants;

import com.github.cliftonlabs.json_simple.JsonKey;

public enum Attributes implements JsonKey {

    ID,
    NAME,
    COLOR,
    WEIGHT,
    HEIGHT,
    OWNER_NAME,
    OWNER_AGE,
    ANIMALS_NUMBER;

    private static final String REG_UNDERSCORE = "_";
    private static final String REG_SCORE = "-";

    @Override
    public String getKey() {
        return this.name().toLowerCase().replaceAll(REG_UNDERSCORE, REG_SCORE);
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }
}
