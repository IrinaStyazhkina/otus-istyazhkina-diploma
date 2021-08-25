package ru.otus.istyazhkina.constructor.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.istyazhkina.constructor.advice.AppExceptionHandler;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;
import ru.otus.istyazhkina.constructor.security.SecurityConfiguration;
import ru.otus.istyazhkina.constructor.service.EmployeeService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
@Import({EmployeeController.class, AppExceptionHandler.class})
@ContextConfiguration(classes = {SecurityConfiguration.class, ControllerTestsConfiguration.class})
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    private Employee employee = Employee.builder()
            .id("1")
            .name("Анна")
            .secondName("Вячеславовна")
            .surname("Кокорина")
            .position(EmployeePosition.builder().id("1").title("Юрист").build())
            .passDate("22.07.1998")
            .passNumber("2314 3423421")
            .passIssued("ОВД пос.Мурино Ленинградской области")
            .build();

    private EmployeePosition employeePosition = EmployeePosition.builder()
            .id("1")
            .title("Юрист")
            .build();

    private static final String arrayJsonEmployees = "[{\"id\":\"1\",\"employeePositionDTO\":{\"id\":\"1\",\"title\":\"Юрист\"},\"name\":\"Анна\",\"secondName\":\"Вячеславовна\",\"surname\":\"Кокорина\",\"passDate\":\"22.07.1998\",\"passNumber\":\"2314 3423421\", \"passIssued\":\"ОВД пос.Мурино Ленинградской области\"}]";
    private static final String arrayJsonEmployeePositions = "[{\"id\":\"1\", \"title\":\"Юрист\"}]";
    private static final String singleEmployeeJson = "{\"id\":\"1\",\"employeePositionDTO\":{\"id\":\"1\",\"title\":\"Юрист\"},\"name\":\"Анна\",\"secondName\":\"Вячеславовна\",\"surname\":\"Кокорина\",\"passDate\":\"22.07.1998\",\"passNumber\":\"2314 3423421\", \"passIssued\":\"ОВД пос.Мурино Ленинградской области\"}";

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnEmployeesList() {
        when(employeeService.getAllEmployee()).thenReturn(List.of(employee));
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().is(200))
                .andExpect(content().json(arrayJsonEmployees));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnEmployeePositionsList() {
        when(employeeService.getAllEmployeePositions()).thenReturn(List.of(employeePosition));
        mockMvc.perform(get("/api/positions"))
                .andExpect(status().is(200))
                .andExpect(content().json(arrayJsonEmployeePositions));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnEmployeeById() {
        when(employeeService.getEmployeeById("1")).thenReturn(employee);
        mockMvc.perform(get("/api/employee/1"))
                .andExpect(status().is(200))
                .andExpect(content().json(singleEmployeeJson));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldAddEmployee() {
        Employee newEmployee = Employee.builder()
                .name("Анна")
                .secondName("Вячеславовна")
                .surname("Кокорина")
                .position(EmployeePosition.builder().id("1").title("Юрист").build())
                .passDate("22.07.1998")
                .passNumber("2314 3423421")
                .passIssued("ОВД пос.Мурино Ленинградской области")
                .build();
        when(employeeService.getPositionByPositionId("1")).thenReturn(employeePosition);
        when(employeeService.addNewEmployee(newEmployee)).thenReturn(employee);
        mockMvc.perform(post("/employee/add")
                .contentType("application/json;charset=utf-8")
                .content("{\"employeePositionDTO\":{\"id\":\"1\"},\"name\":\"Анна\",\"secondName\":\"Вячеславовна\",\"surname\":\"Кокорина\",\"passDate\":\"22.07.1998\",\"passNumber\":\"2314 3423421\", \"passIssued\":\"ОВД пос.Мурино Ленинградской области\"}"))
                .andExpect(status().is(201))
                .andExpect(content().json(singleEmployeeJson));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldUpdateEmployee() {
        Employee updateEmployee = Employee.builder()
                .name("Мария")
                .secondName("Сергеевна")
                .surname("Высокая")
                .position(EmployeePosition.builder().id("1").title("Юрист").build())
                .passDate("22.07.1998")
                .passNumber("2314 3423421")
                .passIssued("ОВД пос.Мурино Ленинградской области")
                .build();

        Employee updatedEmployee = Employee.builder()
                .id("2")
                .name("Мария")
                .secondName("Сергеевна")
                .surname("Высокая")
                .position(EmployeePosition.builder().id("1").title("Юрист").build())
                .passDate("22.07.1998")
                .passNumber("2314 3423421")
                .passIssued("ОВД пос.Мурино Ленинградской области")
                .build();

        String jsonContent = "{\"id\":\"2\",\"employeePositionDTO\":{\"id\":\"1\",\"title\":\"Юрист\"},\"name\":\"Мария\",\"secondName\":\"Сергеевна\",\"surname\":\"Высокая\",\"passDate\":\"22.07.1998\",\"passNumber\":\"2314 3423421\", \"passIssued\":\"ОВД пос.Мурино Ленинградской области\"}";

        when(employeeService.getPositionByPositionId("1")).thenReturn(employeePosition);
        when(employeeService.updateEmployee("2", updateEmployee)).thenReturn(updatedEmployee);

        mockMvc.perform(put("/employee/2")
                .contentType("application/json;charset=utf-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(content().json(jsonContent));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldDeleteEmployee() {
        mockMvc.perform(delete("/employee/3"))
                .andExpect(status().is(200));
    }

}