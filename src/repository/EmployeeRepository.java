package repository;

import domain.Employee;
import exception.NotFoundEmployeeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {

    private int sequence = 1;
    private final Map<Integer, Employee> storage = new HashMap<>();

    public void saveEmployee(String name, int wage) {
        Employee newEmployee = new Employee(sequence, name, wage);

        storage.put(sequence++, newEmployee);
    }

    public Employee findEmployeeById(int id) throws NotFoundEmployeeException {
        if (!storage.containsKey(id)) {
            throw new NotFoundEmployeeException();
        }

        return storage.get(id);
    }

    public List<Employee> findAllEmployee() {
        return new ArrayList<>(storage.values());
    }

    public Employee deleteEmployeeById(int id) throws NotFoundEmployeeException {
        if (!storage.containsKey(id)) {
            throw new NotFoundEmployeeException();
        }

        return storage.remove(id);
    }
}
