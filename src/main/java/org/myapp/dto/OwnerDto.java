package org.myapp.dto;

/**
 * OwnerDto class provide all properties of {@link org.myapp.entity.Owner} for business logic.
 */
public final class OwnerDto extends BaseDto {
    private final long ownerId;
    private final String ownerName;
    private final int ownerAge;
    private final int animalsAmount;


    private OwnerDto(long ownerId, String ownerName, int ownerAge, int animalsAmount) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerAge = ownerAge;
        this.animalsAmount = animalsAmount;
    }

    /**
     * You can create OwnerDto only via this builder.
     */
    public static final class Builder {
        private long ownerId;
        private String ownerName;
        private int ownerAge;
        private int animalsAmount;

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

        public OwnerDto build() {
            return new OwnerDto(ownerId, ownerName, ownerAge, animalsAmount);
        }
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
}
