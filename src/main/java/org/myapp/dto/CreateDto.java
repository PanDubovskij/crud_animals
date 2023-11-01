package org.myapp.dto;

@Deprecated
public final class CreateDto {

    private final String name;
    private final String color;

    private final int barkingVolume;
    private final int weight;
    private final int height;
    private final String ownerName;

    private final int ownerAge;

    public static final class Builder {
        private String name;
        private String color;
        private int barkingVolume;
        private int weight;
        private int height;
        private String ownerName;
        private int ownerAge;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setBarkingVolume(int barkingVolume) {
            this.barkingVolume = barkingVolume;
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

        public CreateDto build() {
            return new CreateDto(name, color, barkingVolume, weight, height, ownerName, ownerAge);
        }
    }


    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getBarkingVolume() {
        return barkingVolume;
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

    private CreateDto(String name, String color, int barkingVolume, int weight, int height, String ownerName, int ownerAge) {
        this.name = name;
        this.color = color;
        this.barkingVolume = barkingVolume;
        this.weight = weight;
        this.height = height;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
    }

    @Override
    public String toString() {
        return "CreateDto{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", barkingVolume=" + barkingVolume +
                ", weight=" + weight +
                ", height=" + height +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAge=" + ownerAge +
                '}';
    }
}
