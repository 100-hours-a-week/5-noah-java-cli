package thread;

import domain.Coffee;
import utils.Utils;

public class GuestThread extends Thread {

    private final Exchange exchange;
    private final int money;

    public GuestThread(Exchange exchange) {
        this.exchange = exchange;
        money = Utils.getRandomIntBetween(5, 12);
    }

    @Override
    public void run() {
        while (true) {
            if (exchange.hasCoffee()) {
                Coffee coffee = exchange.getCoffee();

                int price = coffee.getPrice();

                if (price < money) {
                    exchange.payPrice(0);

                    System.out.println("<<system>> 손님이 커피를 들고 도망갔습니다!");
                } else {
                    int tip = Utils.getRandomIntBetween(0, 3);

                    exchange.payPrice(price + tip);

                    System.out.println("<<system>> 손님이 " + price + "원과 팁 " + tip + "원을 지불했습니다.");
                }

                break;
            } else {
                System.out.println("<<system>> 손님이 커피를 기다리고 있습니다.");
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
