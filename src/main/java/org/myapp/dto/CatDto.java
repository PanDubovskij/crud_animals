package org.myapp.dto;

/**
 * CatDto class provide all properties of {@link org.myapp.entity.Cat} and {@link org.myapp.entity.Owner}
 */
public final class CatDto extends BaseDto {

    private final long id;
    private final String name;
    private final String color;
    private final int weight;
    private final int height;
    private final long ownerId;
    private final String ownerName;
    private final int ownerAge;
    private final int animalsAmount;


    private CatDto(long id, String name, String color, int weight, int height, long ownerId, String ownerName, int ownerAge, int animalsAmount) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
        this.animalsAmount = animalsAmount;
    }

    /**
     * You can create CatDto only via this builder.
     */
    public static final class Builder {
        private long id;
        private String name;
        private String color;
        private int weight;
        private int height;
        private long ownerId;
        private String ownerName;
        private int ownerAge;

        private int animalsAmount;


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

        public Builder setOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder setOwnerAge(int ownerAge) {
            this.ownerAge = ownerAge;
            return this;
        }

        public Builder setAnimalsAmount(int animalsAmount) {
            this.animalsAmount = animalsAmount;
            return this;
        }

        public CatDto build() {
            return new CatDto(id, name, color, weight, height, ownerId, ownerName, ownerAge, animalsAmount);
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

    public long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getOwnerAge() {
        return ownerAge;
    }

    public int getAnimalsAmount() {
        return animalsAmount;
    }

    @Override
    public String toString() {
        return "CatDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAge=" + ownerAge +
                ", animalsAmount=" + animalsAmount +
                '}';
    }
}
