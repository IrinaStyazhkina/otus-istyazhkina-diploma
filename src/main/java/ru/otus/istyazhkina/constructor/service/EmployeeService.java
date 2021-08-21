package ru.otus.istyazhkina.constructor.service;

import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee getEmployeeById(String id) throws DataNotFoundException;

    Employee addNewEmployee(Employee employee);

    List<EmployeePosition> getAllEmployeePositions();

    EmployeePosition getPositionByPositionId(String id) throws DataNotFoundException;

    void deleteEmployee(String employeeId);

    Employee updateEmployee(String employeeId, Employee employee) throws DataNotFoundException;
}
