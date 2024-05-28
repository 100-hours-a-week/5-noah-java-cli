package Facade;

import domain.Bean;
import domain.Employee;

import java.util.List;

public interface CafeViewable {

    // 게임의 턴을 반환합니다.
    int getCurrentTurn();

    // 카페 보유 금액을 반환합니다.
    int getMoney();

    // 카페에 일할 수 있는 직원들을 반환합니다.
    List<Employee> getEmployee();

    // 보유한 원두들을 반환합니다.
    List<Bean> getBean();
}
