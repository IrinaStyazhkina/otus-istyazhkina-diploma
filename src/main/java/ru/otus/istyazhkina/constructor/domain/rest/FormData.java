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

    CompanyDTO company;
    EmployeeDTO employee;
    String city;
    String docDate;
    String salaryDate;
    Integer salaryRub;
    Integer salaryCop;
    Power power;
    String expireDate;

}
