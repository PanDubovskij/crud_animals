package org.myapp.entity;

/**
 * This class provides Owner entity for interactions with dao
 */
public final class Owner extends BaseEntity {
    private final long id;
    private final String name;
    private final int age;
    private final int animalsAmount;

    private Owner(long id, String name, int age, int animalsAmount) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.animalsAmount = animalsAmount;
    }

    /**
     * You can create Owner only via this builder.
     */
    public static final class Builder {
        private long id;
        private String name;
        private int age;
        private int animalsAmount;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setAnimalsAmount(int animalsAmount) {
            this.animalsAmount = animalsAmount;
            return this;
        }

        public Owner build() {
            return new Owner(id, name, age, animalsAmount);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAnimalsAmount() {
        return animalsAmount;
    }


    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", animalsAmount=" + animalsAmount +
                '}';
    }
}
