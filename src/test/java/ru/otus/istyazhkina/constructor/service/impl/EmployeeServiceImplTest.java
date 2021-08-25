package ru.otus.istyazhkina.constructor.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;
import ru.otus.istyazhkina.constructor.repository.EmployeePositionsRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.service.EmployeeService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePositionsRepository employeePositionsRepository;

    private final Employee employee = Employee.builder()
            .id("1")
            .position(EmployeePosition.builder().id("1").title("Инженер").build())
            .name("Федор")
            .secondName("Олегович")
            .surname("Петров")
            .passDate("29.09.2000")
            .passNumber("1234 547625")
            .passIssued("УВД г.Самара")
            .build();

    private final List<Employee> employees = List.of(employee);

    private final EmployeePosition employeePosition = EmployeePosition.builder().id("1").title("Инженер").build();

    @BeforeEach
    void setUp() {
        Mockito.when(employeePositionsRepository.findAll()).thenReturn(List.of(employeePosition));
        Mockito.when(employeePositionsRepository.findById("1")).thenReturn(Optional.of(employeePosition));
        Mockito.when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));
    }

    @Test
    void shouldReturnAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> allEmployee = employeeService.getAllEmployee();
        assertThat(allEmployee).isEqualTo(employees);
    }

    @Test
    @SneakyThrows
    void shouldReturnEmployeeById() {
        Employee employeeById = employeeService.getEmployeeById("1");
        assertThat(employeeById).isEqualTo(employee);
    }

    @Test
    void shouldReturnAllEmployeePositions() {
        List<EmployeePosition> allEmployeePositions = employeeService.getAllEmployeePositions();
        assertThat(allEmployeePositions).isNotEmpty().containsExactly(employeePosition);
    }

    @Test
    @SneakyThrows
    void shouldReturnEmployeePositionById() {
        EmployeePosition employeePositionById = employeeService.getPositionByPositionId("1");
        assertThat(employeePositionById).isEqualTo(employeePosition);
    }

    @Test
    void shouldAddNewEmployee() {
        Employee newEmployee = Employee.builder()
                .id("2")
                .name("Анна")
                .secondName("Владимировна")
                .surname("Куркова")
                .passIssued("УВД Ленинского района г.Омска")
                .passNumber("4536 267635")
                .passDate("23.03.1998")
                .build();
        Mockito.when(employeeRepository.save(newEmployee)).thenReturn(newEmployee);
        Employee createdEmployee = employeeService.addNewEmployee(newEmployee);
        assertThat(createdEmployee).isEqualTo(newEmployee);
    }

    @Test
    @SneakyThrows
    void shouldUpdateEmployee() {
        employee.setName("Виктория");
        employee.setSurname("Лучкова");
        employee.setSecondName("Алексеевна");
        employee.setPassDate("21.11.1980");
        employee.setPassNumber("7839 874987");
        employee.setPassIssued("РУВД Ленинского района г.Красноярска");
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee updatedEmployee = employeeService.updateEmployee("1", employee);
        assertThat(updatedEmployee).isEqualTo(employee);
    }

    @Test
    void shouldDeleteEmloyee() {
        Mockito.doNothing().when(employeeRepository).deleteById("3");
        employeeService.deleteEmployee("3");
        assertThatNoException();
    }


}