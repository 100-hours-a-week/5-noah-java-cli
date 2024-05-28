package Facade;

import domain.Coffee;
import domain.Employee;
import exception.AlreadyHiredEmployeeException;
import exception.NotFoundBeanException;
import exception.NotFoundEmployeeException;
import exception.OutOfMoneyException;

public interface CafeService {

    // 카페 소지금을 money만큼 증가 시킬 수 있습니다.
    void addMoney(int money);

    // 직원 id를 통해 해당 직원이 요청한 작업을 수행할 수 있는지 확인합니다.
    boolean isWorkedEmployee(int employeeId);

    // 이름과 일급 파라미터를 받아 직원을 직원 저장소에 등록합니다.
    // 같은 턴에 이미 직원을 고용했다면 AlreadyHiredEmployeeException이 발생합니다.
    void hireEmployee(String name, int wage) throws AlreadyHiredEmployeeException;

    // 직원 id를 통해 직원 저장소에서 직원을 삭제합니다, 삭제한 직원을 반환합니다. (엔딩에 사용)
    // 해당 직원을 찾을 수 없다면 NotFoundEmployeeException이 발생합니다.
    Employee fireEmployeeById(int EmployeeId) throws NotFoundEmployeeException;

    // 직원 id를 통해 해당 직원의 정보를 반환합니다.
    // 해당 직원이 없다면 NotFoundEmployeeException이 발생합니다.
    Employee findEmployeeById(int EmployeeId) throws NotFoundEmployeeException;

    // 직원 id, 원두 이름, 수량을 받아 원두를 주문합니다.
    // 수향*2가 소지금을 넘는다면 OutOfMoneyException이 발생합니다.
    void orderBean(int employeeId, String name, int amount) throws OutOfMoneyException;

    // 해당 메서드는 스레드의 랜덤 이벤트를 위해 사용됩니다.
    // 수량만큼 노아 원두를 추가합니다.
    void noahOrderBean(int amount);

    // 커피를 만들기 위해 사용할 원두를 선택하는 메서드입니다.
    // 해당 원두가 없다면 NotFoundBeanException이 발생합니다.
    String useBeanByBeanId(int beanId) throws NotFoundBeanException;

    // 입력을 바탕으로 커피를 만들어 반환하는 메서드입니다.
    Coffee makeCoffee(Employee employee, String beanName, int coffeeTypeAction, int coffeeSizeAction, int coffeeTemperatureAction);

    // 게임에서 턴을 넘길 때 발생하는 이벤트 행위와 이에 대한 결과 문자열을 반환합니다.
    String[] nextDayAndGetStringArray();

    // 게임이 끝났는지 확인하는 메서드입니다.
    boolean isGameOver();

    // 목표 금액을 당성해는지 확인하는 메서드입니다.
    boolean isSuccess();

    // 게임에 대한 결과를 문자열 리트로 반환하는 메서드입니다.
    String[] getEndingStringArray();
}
