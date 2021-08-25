package ru.otus.istyazhkina.constructor.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.Power;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormData {

    private CompanyDTO company;
    private EmployeeDTO employee;
    private String city;
    private String docDate;
    private String salaryDate;
    private Integer salaryRub;
    private Integer salaryCop;
    private Power power;
    private String expireDate;

}
