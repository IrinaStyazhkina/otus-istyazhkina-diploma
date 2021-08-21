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
    String id;
    @Field("name")
    String name;
    @Field("second_name")
    String secondName;
    @Field("surname")
    String surname;
    @Field("position")
    EmployeePosition position;
    @Field("pass_number")
    String passNumber;
    @Field("pass_date")
    String passDate;
    @Field("pass_issued")
    String passIssued;

}
