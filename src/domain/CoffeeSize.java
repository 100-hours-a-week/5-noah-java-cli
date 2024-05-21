package domain;

public enum CoffeeSize {
    MEDIUM(0), LARGE(1);

    private final int priceAdjustment;

    CoffeeSize(int priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public int getPriceAdjustment() {
        return priceAdjustment;
    }
}
