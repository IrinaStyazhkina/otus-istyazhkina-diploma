package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("second_name")
    private String secondName;
    @Field("surname")
    private String surname;
    @Field("position")
    private EmployeePosition position;
    @Field("pass_number")
    private String passNumber;
    @Field("pass_date")
    private String passDate;
    @Field("pass_issued")
    private String passIssued;

}
