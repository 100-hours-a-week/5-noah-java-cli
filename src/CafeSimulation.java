import config.Config;
import domain.*;
import exception.AlreadyHiredEmployeeException;
import exception.NotFoundBeanException;
import exception.NotFoundEmployeeException;
import exception.OutOfMoneyException;
import repository.BeanRepository;
import repository.EmployeeRepository;
import Facade.CafeFacade;
import thread.EmployeeThread;
import thread.Exchange;
import thread.GuestThread;
import utils.Utils;

public class CafeSimulation {

    private final CafeFacade cafeFacade = new CafeFacade(new BeanRepository(), new EmployeeRepository());

    public void run() {
        startRandomEvent();

        while (!cafeFacade.isGameOver()) {
            System.out.println("\n=============== " + cafeFacade.getCurrentTurn() + " DAY " + "===============");
            System.out.println(" - 목표 금액: 100원");
            System.out.println(" - 현재 금액: " + cafeFacade.getMoney() + "원");
            System.out.println("=============== " + " EMPLOYEE INFO " + "===============");
            for (Employee employee : cafeFacade.getEmployee()) {
                System.out.println(" - id: " + employee.getId() + ", 이름: " + employee.getName() + ", 일급: " + employee.getWage() + ", 행동: " + (cafeFacade.isWorkedEmployee(employee.getId()) ? 'Y' : 'N'));
            }

            Utils.printStringArraySlowly(new String[]{"<<system>> 어떤 행동을 하시겠습니까?", " 1. 직원 고용, 하루 한번만 가능", " 2. 직원 해고", " 3. 원두 발주", " 4. 원두 확인", " 5. 커피 만들기, 판매", " 0. 마감"}, 300);

            int action = Utils.getAction(0, 1, 2, 3, 4, 5);

            if (action == 0) {
                Utils.printStringArraySlowly(cafeFacade.nextDayAndGetStringArray(), 100);
            } else if (action == 1) {
                hireEmployee();
            } else if (action == 2) {
                fireEmployee();
            } else if (action == 3) {
                orderBeen();
            } else if (action == 4) {
                findAllBean();
            } else if (action == 5) {
                makeCoffeeAndSellCoffee();
            }
        }

        Utils.printStringArraySlowly(cafeFacade.getEndingStringArray(), 100);
    }

    private void hireEmployee() {
        String name = Utils.getRandomString();
        int wage = Utils.getRandomIntBetween(1, 5);

        Utils.printStringArraySlowly(new String[]{"<<system>> 지원서가 들어왔습니다!", " - 이름: " + name + ", 일급: " + wage, "<<system>> 이를 고용할까요?", " 1. 고용", " 0. 거절"}, 300);

        int action = Utils.getAction(0, 1);

        if (action == 1) {
            try {
                cafeFacade.hireEmployee(name, wage);
            } catch (AlreadyHiredEmployeeException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.println("<<system>> " + name + "을 고용했습니다!");
        }
    }

    private void fireEmployee() {
        System.out.println("<<system>> 어떤 직원을 해고할까요? 직원 id를 입력하세요.");

        try {
            int EmployeeById = Utils.getInt();

            if (cafeFacade.isWorkedEmployee(EmployeeById)) {
                System.out.println("<<system>> 일한 직원은 해고할 수 없습니다, 당신은 악덕 사장인가요?");
                return;
            }

            Employee firedEmployee = cafeFacade.fireEmployeeById(EmployeeById);

            System.out.println("<<system>> " + firedEmployee.getName() + "은/는 해고 되었습니다!");
        } catch (NotFoundEmployeeException e) {
            System.out.println("<<system>> " + e.getMessage());
        }
    }

    private void orderBeen() {
        System.out.println("<<system>> 어떤 직원이 주문할까요? 직원 id를 입력하세요.");

        Employee employee;

        try {
            employee = cafeFacade.findEmployeeById(Utils.getInt());
        } catch (NotFoundEmployeeException e) {
            System.out.println("<<system>> " + e.getMessage());
            return;
        }

        if (cafeFacade.isWorkedEmployee(employee.getId())) {
            System.out.println("<<system>> 해당 직원은 예약된 작업이 있습니다.");
            return;
        }

        System.out.println("<<system>> 원두와 수량을 입력하세요. (원두 이름, 엔터, 수량))");

        String beanName = Utils.getString();
        int beanAmount = Utils.getInt();

        System.out.println(beanName);

        try {
            cafeFacade.orderBean(employee.getId(), beanName, beanAmount);

            System.out.println("<<system>> " + beanName + "를 " + beanAmount + "개 주문했습니다.");
        } catch (OutOfMoneyException e) {
            System.out.println("<<system>> " + e.getMessage());
        }
    }

    private void findAllBean() {
        System.out.println("=============== " + "BEEN LIST " + "===============");
        for (Bean bean : cafeFacade.getBean()) {
            System.out.println(" - id: " + bean.getId() + ", 이름: " + bean.getName() + ", 수량: " + bean.getAmount() + ", 소비기한: " + bean.getExpirationTurn());
        }
    }

    private void makeCoffeeAndSellCoffee() {
        System.out.println("<<system>> 커피를 만들 직원을 id로 선택하세요.");

        Employee employee;

        try {
            employee = cafeFacade.findEmployeeById(Utils.getInt());
        } catch (NotFoundEmployeeException e) {
            System.out.println("<<system>> " + e.getMessage());
            return;
        }

        if (cafeFacade.isWorkedEmployee(employee.getId())) {
            System.out.println("<<system>> 해당 직원은 예약된 작업이 있습니다.");
            return;
        }

        System.out.println("<<system>> 원두를 id로 선택하세요.");

        String beanName;

        try {
            beanName = cafeFacade.useBeanByBeanId(Utils.getInt());
        } catch (NotFoundBeanException e) {
            System.out.println(e.getMessage());
            return;
        }

        Exchange exchange = new Exchange();

        EmployeeThread employeeThread = new EmployeeThread(exchange);
        GuestThread guestThread = new GuestThread(exchange);

        employeeThread.start();
        guestThread.start();

        employeeThread.setCoffee(cafeFacade.makeCoffee(employee, beanName, getCoffeeTypeAction(), getCoffeeSizeAction(), getCoffeeTemperatureAction()));

        // INFO: 7주차 과제를 위한 스레드 로직
        while (true) {
            if (exchange.getMoney() != -1) {
                int money = exchange.getMoney();

                cafeFacade.addMoney(money);

                System.out.println("<<system>> " + money + "원을 정산했습니다.");
                break;
            } else {
                System.out.println("<<system>> " + "커피 만들고, 계산 중...");
            }

            // 지연
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getCoffeeTypeAction() {
        System.out.println("<<system>> 커피를 선택하세요.");
        System.out.println("<<system>> 1. 에스프레소");
        System.out.println("<<system>> 2. 아메리카노");
        System.out.println("<<system>> 3. 카페라떼");

        return Utils.getAction(1, 2, 3);
    }

    private int getCoffeeSizeAction() {
        System.out.println("<<system>> 커피를 크기 선택하세요.");
        System.out.println("<<system>> 1. 중간");
        System.out.println("<<system>> 2. 큰");

        return Utils.getAction(1, 2);
    }

    private int getCoffeeTemperatureAction() {
        System.out.println("<<system>> 커피를 온도를 선택하세요.");
        System.out.println("<<system>> 1. 뜨겁게");
        System.out.println("<<system>> 2. 차갑게");

        return Utils.getAction(1, 2);
    }

    private void startRandomEvent() {
        Thread thread = new Thread(() -> {
            try {
                int i = Config.MAX_EVENT;

                while (i-- > 0) {
                    Thread.sleep(Config.MAX_EVENT_TIME * 1000L);
                    if (Utils.getProbability(20)) {
                        int beanAmount = Utils.getRandomIntBetween(1, 3);

                        cafeFacade.noahOrderBean(beanAmount);

                        System.out.println("<<system>> noah가 원두를 " + beanAmount + "개" + " 채웠습니다!");
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
}
