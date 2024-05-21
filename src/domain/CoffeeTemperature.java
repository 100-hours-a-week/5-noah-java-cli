package domain;

public enum CoffeeTemperature {
    HOT(0), ICED(1);

    private final int priceAdjustment;

    CoffeeTemperature(int priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public int getPriceAdjustment() {
        return priceAdjustment;
    }
}
