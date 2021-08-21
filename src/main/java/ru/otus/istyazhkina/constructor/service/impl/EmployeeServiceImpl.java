package ru.otus.istyazhkina.constructor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.repository.EmployeePositionsRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.service.EmployeeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeePositionsRepository employeePositionsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(String id) throws DataNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Employee by provided id is not found"));
    }

    @Override
    @Transactional
    public Employee addNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeePosition> getAllEmployeePositions() {
        return employeePositionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeePosition getPositionByPositionId(String id) throws DataNotFoundException {
        return employeePositionsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No position found by provided id!"));
    }

    @Override
    @Transactional
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    @Transactional
    public Employee updateEmployee(String employeeId, Employee employee) throws DataNotFoundException {
        Employee employeeFromDB = employeeRepository.findById(employeeId).orElseThrow(() -> new DataNotFoundException("Employee by provided id is not found!"));
        employeeFromDB.setName(employee.getName());
        employeeFromDB.setSecondName(employee.getSecondName());
        employeeFromDB.setSurname(employee.getSurname());
        employeeFromDB.setPosition(employee.getPosition());
        return employeeRepository.save(employeeFromDB);
    }
}
