package domain;

public enum CoffeeType {
    ESPRESSO(4), AMERICANO(5), CAFE_LATTE(6);

    private final int priceAdjustment;

    CoffeeType(int priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public int getPriceAdjustment() {
        return priceAdjustment;
    }
}
