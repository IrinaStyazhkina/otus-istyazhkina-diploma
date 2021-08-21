package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    String id;
    @Field("company_form")
    CompanyLegalForm companyLegalForm;
    @Field("company_name")
    String companyName;
    @Field("manager_position")
    ManagerPosition position;
    @Field("manager_name")
    String managerName;
    @Field("manager_second_name")
    String managerSecondName;
    @Field("manager_surname")
    String managerSurname;
    @Field("inn")
    String inn;
}
