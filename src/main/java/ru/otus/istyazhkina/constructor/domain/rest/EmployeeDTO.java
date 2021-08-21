package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    String id;
    @NotEmpty(message = "Please provide employee's name")
    String name;
    String secondName;
    @NotEmpty(message = "Please provide employee's surname")
    String surname;
    @NotEmpty(message = "Please provide employee's position")
    EmployeePositionDTO employeePositionDTO;
    @NotEmpty(message = "Please provide employee's pass number")
    String passNumber;
    @NotEmpty(message = "Please provide employee's pass date")
    String passDate;
    @NotEmpty(message = "Please provide information who issued employee's pass")
    String passIssued;

    public static Employee toEntity(EmployeeDTO dto) {
        return Employee.builder()
                .name(dto.getName())
                .secondName(dto.getSecondName())
                .surname(dto.getSurname())
                .passDate(dto.getPassDate())
                .passNumber(dto.getPassNumber())
                .passIssued(dto.getPassIssued())
                .build();
    }

    public static EmployeeDTO toDto(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .secondName(employee.getSecondName())
                .surname(employee.getSurname())
                .employeePositionDTO(EmployeePositionDTO.toDto(employee.getPosition()))
                .passDate(employee.getPassDate())
                .passIssued(employee.getPassIssued())
                .passNumber(employee.getPassNumber())
                .build();
    }

}
