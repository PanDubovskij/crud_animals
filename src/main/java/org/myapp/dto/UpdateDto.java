package org.myapp.dto;

import org.myapp.entity.Owner;

public final class UpdateDto {
    private final long id;
    private final String name;
    private final int weight;
    private final int height;
    private final Owner owner;

    public static final class Builder {
        private long id;
        private String name;
        private int weight;
        private int height;
        private Owner owner;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public UpdateDto build() {
            return new UpdateDto(id, name, weight, height, owner);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public Owner getOwner() {
        return owner;
    }

    private UpdateDto(long id, String name, int weight, int height, Owner owner) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.owner = owner;
    }
}
