package domain;

public class Employee {

    private final int id;
    private final String name;
    private int wage; // 알바비

    public Employee(int id, String name, int wage) {
        this.id = id;
        this.name = name;
        this.wage = wage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }
}
