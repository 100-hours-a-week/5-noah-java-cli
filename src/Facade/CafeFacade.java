package Facade;

import config.Config;
import domain.*;
import domain.EndingInfo;
import exception.AlreadyHiredEmployeeException;
import exception.NotFoundBeanException;
import exception.NotFoundEmployeeException;
import exception.OutOfMoneyException;
import repository.BeanRepository;
import repository.EmployeeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CafeFacade {

    private final Set<Integer> workedEmployees = new HashSet<>();
    private final EndingInfo endingInfo = new EndingInfo();

    private int currentTurn = 0;
    private int money = 50;

    private boolean isHire = false;

    private final BeanRepository beanRepository;
    private final EmployeeRepository employeeRepository;

    public CafeFacade(BeanRepository beanRepository, EmployeeRepository employeeRepository) {
        this.beanRepository = beanRepository;
        this.employeeRepository = employeeRepository;
    }

    // 화면 출력 용
    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getMoney() {
        return money;
    }

    public List<Employee> getEmployee() {
        return employeeRepository.findAllEmployee();
    }

    public List<Bean> getBean() {
        return beanRepository.findAllBean();
    }
    // 화면 출력 끝

    public void addMoney(int money) {
        this.money += money;
    }

    public boolean isWorkedEmployee(int employeeId) {
        return workedEmployees.contains(employeeId);
    }

    public void hireEmployee(String name, int wage) throws AlreadyHiredEmployeeException {
        if (isHire) {
            throw new AlreadyHiredEmployeeException();
        }

        employeeRepository.saveEmployee(name, wage);

        isHire = true;
    }

    public Employee fireEmployeeById(int EmployeeId) throws NotFoundEmployeeException {
        Employee firedEmployee = employeeRepository.deleteEmployeeById(EmployeeId);

        endingInfo.addFiredEmployee(firedEmployee);

        return firedEmployee;
    }

    public Employee findEmployeeById(int EmployeeId) throws NotFoundEmployeeException {
        return employeeRepository.findEmployeeById(EmployeeId);
    }

    public void orderBean(int EmployeeId, String name, int amount) throws OutOfMoneyException {
        int expenses = amount * 2;

        if (money - expenses < 0) {
            throw new OutOfMoneyException();
        }

        money -= expenses;

        workedEmployees.add(EmployeeId);

        beanRepository.saveBean(name, amount, currentTurn);
    }

    public void noahOrderBean(int amount) {
        beanRepository.updateNoahBean(amount);
    }

    public String useBeanByBeanId(int beanId) throws NotFoundBeanException {
        return beanRepository.useBeanById(beanId);
    }

    public Coffee makeCoffee(Employee employee, String beanName, int coffeeTypeAction, int coffeeSizeAction, int coffeeTemperatureAction) {
        workedEmployees.add(employee.getId());

        Coffee madeCoffee = new Coffee.Builder()
                .beanName(beanName)
                .coffeeType(getCoffeeType(coffeeTypeAction))
                .coffeeSize(getCoffeeSize(coffeeSizeAction))
                .coffeeTemperature(getCoffeeTemperature(coffeeTemperatureAction))
                .madeBy(employee.getName()).build();

        endingInfo.addMadeCoffee(madeCoffee);

        return madeCoffee;
    }

    private CoffeeType getCoffeeType(int action) {
        if (action == 1) {
            return CoffeeType.ESPRESSO;
        } else if (action == 2) {
            return CoffeeType.AMERICANO;
        }
        return CoffeeType.CAFE_LATTE;
    }

    private CoffeeSize getCoffeeSize(int action) {
        return action == 1 ? CoffeeSize.MEDIUM : CoffeeSize.LARGE;
    }

    private CoffeeTemperature getCoffeeTemperature(int action) {
        return action == 1 ? CoffeeTemperature.HOT : CoffeeTemperature.ICED;
    }

    public String[] nextDayAndGetStringArray() {
        // 소비 기한이 지난 원두 버리기, 직원들에게 일급 지급
        String[] strings = new String[]{throwBeanWhenExpirationTurnAndGetMessage(), payWageAndGetMessage()};

        isHire = false;

        workedEmployees.clear();

        currentTurn++;

        return strings;
    }

    private String throwBeanWhenExpirationTurnAndGetMessage() {
        StringBuilder sb = new StringBuilder();

        for (Bean bean : beanRepository.findAllBean()) {
            if (bean.getExpirationTurn() <= currentTurn) {
                endingInfo.addDiscardedBean(beanRepository.deleteBean(bean.getId()));

                sb.append("<<system>> 소비기한이 지난 ").append(bean.getId()).append("번 ").append(bean.getName()).append("를 버립니다.\n");
            }
        }

        return sb.toString();
    }

    private String payWageAndGetMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("<<system>> 일급 정산\n");
        for (Employee employee : employeeRepository.findAllEmployee()) {
            if (money >= employee.getWage()) {
                money -= employee.getWage();
                sb.append("<<system>> ").append(employee.getId()).append("번 직원 ").append(employee.getName()).append("에게 ").append(employee.getWage()).append("원을 지급했습니다.\n");
            } else {
                try {
                    endingInfo.addRunAwayEmployee(employeeRepository.deleteEmployeeById(employee.getId()));
                } catch (NotFoundEmployeeException ignored) {
                }

                sb.append("<<system>> ").append(employee.getId()).append("번 직원 ").append(employee.getName()).append("에게 ").append(employee.getWage()).append("원을 지급할 수 없습니다,").append("해당 직원은 가게를 떠났습니다.\n");
            }
        }
        return sb.toString();
    }

    public boolean isGameOver() {
        return currentTurn > Config.MAX_TURN || isSuccess() || money == 0;
    }

    public boolean isSuccess() {
        return money > Config.TARGET_AMOUNT;
    }

    public String[] getEndingStringArray() {
        endingInfo.setMoney(money);

        return endingInfo.convertEndingDataToStringArray();
    }
}
