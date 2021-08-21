package ru.otus.istyazhkina.constructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.rest.EmployeeDTO;
import ru.otus.istyazhkina.constructor.domain.rest.EmployeePositionDTO;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("api/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployee()
                .stream()
                .map(EmployeeDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/positions")
    public List<EmployeePositionDTO> getEmployeePositions() {
        return employeeService.getAllEmployeePositions()
                .stream()
                .map(EmployeePositionDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/employee/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeById(@PathVariable("employeeId") String employeeId) throws DataNotFoundException {
        return EmployeeDTO.toDto(employeeService.getEmployeeById(employeeId));
    }

    @PostMapping("/employee/add")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws DataNotFoundException {
        Employee employee = EmployeeDTO.toEntity(employeeDTO);
        employee.setPosition(employeeService.getPositionByPositionId(employeeDTO.getEmployeePositionDTO().getId()));
        return EmployeeDTO.toDto(employeeService.addNewEmployee(employee));
    }


    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO updateEmployee(@PathVariable("employeeId") String employeeId, @Valid @RequestBody EmployeeDTO employeeDTO) throws DataNotFoundException {
        Employee employee = EmployeeDTO.toEntity(employeeDTO);
        employee.setPosition(employeeService.getPositionByPositionId(employeeDTO.getEmployeePositionDTO().getId()));
        return EmployeeDTO.toDto(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") String employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}
