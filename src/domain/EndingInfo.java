package domain;

import java.util.ArrayList;
import java.util.List;

public class EndingInfo {

    private final List<Employee> firedEmployees = new ArrayList<>();
    private final List<Employee> runAwayEmployees = new ArrayList<>();
    private final List<Bean> discardedBeans = new ArrayList<>();
    private final List<Coffee> madeCoffees = new ArrayList<>();
    private int money = 0;

    public void addFiredEmployee(Employee employee) {
        firedEmployees.add(employee);
    }

    public void addRunAwayEmployee(Employee employee) {
        runAwayEmployees.add(employee);
    }

    public void addDiscardedBean(Bean bean) {
        discardedBeans.add(bean);
    }

    public void addMadeCoffee(Coffee coffee) {
        madeCoffees.add(coffee);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String[] convertEndingDataToStringArray() {
        return new String[]{"=============== E N D I N G ===============", getGameResult(), getStringWithFiredEmployee(), getStringWithRunAwayEmployee(), getStringWithDiscardedBean(), getStringWithMadeCoffee()};
    }

    private String getGameResult() {
        return money == 100 ? "<<system>> noah는 목표 금액을 통해 방주를 만들 수 있게 되었다." : "<<system>> noah는 목표 금액이 부족해 방주를 만들 수 없게 되었다.\n";
    }

    private String getStringWithFiredEmployee() {
        if (firedEmployees.isEmpty()) {
            return "<<system>> 당신의 완벽한 관리로 그들은 열심히 일할 수 있었다.\n";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<<system>>");

            for (Employee firedEmployee : firedEmployees) {
                sb.append(' ').append(firedEmployee.getName());
            }

            sb.append(" 끝내 마지막까지 함께 할 수 없었다.\n");

            return sb.toString();
        }
    }

    private String getStringWithRunAwayEmployee() {
        if (runAwayEmployees.isEmpty()) {
            return "<<system>> 당신은 밀리지 않고 직원들의 일급을 챙겨주었기에 그들은 굶지 않았다.\n";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<<system>>");

            for (Employee runAwayEmployee : runAwayEmployees) {
                sb.append(' ').append(runAwayEmployee.getName());
            }

            sb.append(" 돈을 받지 못한 그들은 끝내 당신을 떠나갔다.\n");

            return sb.toString();
        }
    }

    private String getStringWithDiscardedBean() {
        int count = 0;

        for (Bean discardedBean : discardedBeans) {
            count += discardedBean.getAmount();
        }

        return count == 0 ? "<<system>> 당신은 버려지는 원두가 없도록 열심히 관리했다.\n" : "<<system>> 당신의 관리 실패로 총 " + count + "개의 원두가 버려졌다.\n";
    }

    private String getStringWithMadeCoffee() {
        StringBuilder sb = new StringBuilder();
        sb.append("<<system>> 당신이 만든 커피 총 ").append(madeCoffees.size()).append("개").append('\n');

        for (Coffee madeCoffee : madeCoffees) {
            sb.append(" - ").append(madeCoffee.getCoffeeType()).append(", ").append(madeCoffee.getPrice()).append("원").append("\n");
        }

        return sb.toString();
    }
}
