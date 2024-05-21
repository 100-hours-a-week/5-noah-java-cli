package domain;

public class Bean {

    private final int id;
    private final String name;
    private int amount;
    private final int expirationTurn;

    public Bean(int id, String name, int amount, int expirationTurn) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.expirationTurn = expirationTurn;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getExpirationTurn() {
        return expirationTurn;
    }

    public void useBean() {
        this.amount--;
    }
}
