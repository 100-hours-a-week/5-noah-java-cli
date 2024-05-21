package domain;

public class Coffee {

    private final String bean;
    private final CoffeeType coffeeType;
    private final CoffeeSize coffeeSize;
    private final CoffeeTemperature coffeeTemperature;
    private final String madeBy;

    private final int price;

    private Coffee(Builder builder) {
        this.bean = builder.bean;
        this.coffeeType = builder.coffeeType;
        this.coffeeSize = builder.coffeeSize;
        this.coffeeTemperature = builder.coffeeTemperature;
        this.madeBy = builder.madeBy;
        this.price = builder.price;
    }

    public String getBean() {
        return bean;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public CoffeeSize getCoffeeSize() {
        return coffeeSize;
    }

    public CoffeeTemperature getCoffeeTemperature() {
        return coffeeTemperature;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public int getPrice() {
        return price;
    }

    public static class Builder {
        private String bean;
        private CoffeeType coffeeType;
        private CoffeeSize coffeeSize;
        private CoffeeTemperature coffeeTemperature;
        private String madeBy;
        private int price;

        public Builder beanName(String bean) {
            this.bean = bean;
            return this;
        }

        public Builder coffeeType(CoffeeType coffeeType) {
            this.coffeeType = coffeeType;
            if (this.coffeeType != null) {
                this.price += this.coffeeType.getPriceAdjustment();
            }
            return this;
        }

        public Builder coffeeSize(CoffeeSize coffeeSize) {
            this.coffeeSize = coffeeSize;
            if (this.coffeeSize != null) {
                this.price += this.coffeeSize.getPriceAdjustment();
            }
            return this;
        }

        public Builder coffeeTemperature(CoffeeTemperature coffeeTemperature) {
            this.coffeeTemperature = coffeeTemperature;
            if (this.coffeeTemperature != null) {
                this.price += this.coffeeTemperature.getPriceAdjustment();
            }
            return this;
        }

        public Builder madeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public Coffee build() {
            return new Coffee(this);
        }
    }
}
