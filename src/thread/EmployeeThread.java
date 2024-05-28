package thread;

import domain.Coffee;

public class EmployeeThread extends Thread {

    private final Exchange exchange;
    private Coffee coffee;

    public EmployeeThread(Exchange exchange) {
        this.exchange = exchange;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public void run() {
        while (true) {
            if (coffee != null) {
                System.out.println("<<system>> 주문하신 커피 나왔습니다!\n");
                System.out.println("<<system>>  - 커피: " + coffee.getCoffeeType().name());
                System.out.println("<<system>>  - 원두: " + coffee.getBean());
                System.out.println("<<system>>  - 크기: " + coffee.getCoffeeSize());
                System.out.println("<<system>>  - 온도: " + coffee.getCoffeeTemperature());
                System.out.println("<<system>>  - 가격: " + coffee.getPrice());

                exchange.setCoffee(coffee);

                break;
            } else {
                System.out.println("<<system>> 커피를 만드는 직원이 다른 일을 하고 있습니다.");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
