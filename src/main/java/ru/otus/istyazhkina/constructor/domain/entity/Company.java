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
    private String id;
    @Field("company_form")
    private CompanyLegalForm companyLegalForm;
    @Field("company_name")
    private String companyName;
    @Field("manager_position")
    private ManagerPosition position;
    @Field("manager_name")
    private String managerName;
    @Field("manager_second_name")
    private String managerSecondName;
    @Field("manager_surname")
    private String managerSurname;
    @Field("inn")
    private String inn;
}
