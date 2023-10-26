package org.myapp.entity;

public final class Dog extends BaseEntity{
    private final long id;
    private final String name;
    private final String color;
    private final int weight;
    private final int height;
    private final long ownerId;

    public static final class Builder{
        private long id;
        private String name;
        private String color;
        private int weight;
        private int height;
        private long ownerId;

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

        public Builder setOwnerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Dog build() {
            return new Dog(id, name, color, weight, height, ownerId);
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

    public long getOwner() {
        return ownerId;
    }

    private Dog(long id, String name, String color, int weight, int height, long ownerId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
        this.ownerId = ownerId;
    }
}
