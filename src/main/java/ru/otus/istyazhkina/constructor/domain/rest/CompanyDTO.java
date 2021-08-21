package ru.otus.istyazhkina.constructor.domain.rest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.Company;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    String id;
    @NotEmpty(message = "Please provide company legal form")
    CompanyLegalFormDTO companyLegalForm;
    @NotEmpty(message = "Please provide company name")
    String companyName;
    @NotEmpty(message = "Please provide manager position")
    ManagerPositionDTO managerPosition;
    @NotEmpty(message = "Please provide manager name")
    String managerName;
    String managerSecondName;
    @NotEmpty(message = "Please provide manager surname")
    String managerSurname;
    @NotEmpty(message = "Please provide company inn")
    String inn;

    public static Company toEntity(CompanyDTO dto) {
        return Company.builder()
                .companyLegalForm(CompanyLegalFormDTO.toEntity(dto.getCompanyLegalForm()))
                .companyName(dto.getCompanyName())
                .position(ManagerPositionDTO.toEntity(dto.getManagerPosition()))
                .managerName(dto.getManagerName())
                .managerSecondName(dto.getManagerSecondName())
                .managerSurname(dto.getManagerSurname())
                .inn(dto.getInn())
                .build();
    }

    public static CompanyDTO toDto(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .companyLegalForm(CompanyLegalFormDTO.toDto(company.getCompanyLegalForm()))
                .companyName(company.getCompanyName())
                .managerPosition(ManagerPositionDTO.toDto(company.getPosition()))
                .managerName(company.getManagerName())
                .managerSecondName(company.getManagerSecondName())
                .managerSurname(company.getManagerSurname())
                .inn(company.getInn())
                .build();
    }
}
