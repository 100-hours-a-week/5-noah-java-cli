package thread;

import domain.Coffee;

public class Exchange {

    private Coffee coffee;
    private int money = -1;

    public boolean hasCoffee() {
        return coffee != null;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public void payPrice(int price) {
        this.money = price;
    }

    public int getMoney() {
        return money;
    }
}
