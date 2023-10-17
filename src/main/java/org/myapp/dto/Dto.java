package org.myapp.dto;

public final class Dto {

    private final long id;
    private final String name;
    private final String color;
    private final int weight;
    private final int height;
    private final String ownerName;
    private final int ownerAge;
    private final int animalsNumber;

    public static final class Builder {
        private long id;
        private String name;
        private String color;
        private int weight;
        private int height;
        private String ownerName;
        private int ownerAge;
        private int animalsNumber;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
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

        public Builder setOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder setOwnerAge(int ownerAge) {
            this.ownerAge = ownerAge;
            return this;
        }

        public Builder setAnimalsNumber(int animalsNumber) {
            this.animalsNumber = animalsNumber;
            return this;
        }

        public Dto build() {
            return new Dto(id, name, color, weight, height, ownerName, ownerAge, animalsNumber);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getOwnerAge() {
        return ownerAge;
    }

    public int getAnimalsNumber() {
        return animalsNumber;
    }

    private Dto(long id, String name, String color, int weight, int height, String ownerName, int ownerAge, int animalsNumber) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
        this.animalsNumber = animalsNumber;
    }
}
