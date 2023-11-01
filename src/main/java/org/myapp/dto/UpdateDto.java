package org.myapp.dto;

import org.myapp.entity.Owner;

@Deprecated
public final class UpdateDto {
    private final long id;
    private final String name;
    private final int weight;
    private final int height;
    private final String ownerName;
    private final int ownerAge;

    public static final class Builder {
        private long id;
        private String name;
        private int weight;
        private int height;
        private String ownerName;

        private int ownerAge;

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

        public Builder setOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder setOwnerAge(int ownerAge) {
            this.ownerAge = ownerAge;
            return this;
        }

        public UpdateDto build() {
            return new UpdateDto(id, name, weight, height, ownerName, ownerAge);
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

    public String getOwnerName() {
        return ownerName;
    }

    public int getOwnerAge() {
        return ownerAge;
    }

    private UpdateDto(long id, String name, int weight, int height, String ownerName, int ownerAge) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
    }

    @Override
    public String toString() {
        return "UpdateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAge=" + ownerAge +
                '}';
    }
}
