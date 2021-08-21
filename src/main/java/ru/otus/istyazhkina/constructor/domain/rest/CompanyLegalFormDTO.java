package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLegalFormDTO {

    String id;
    String title;

    public static CompanyLegalForm toEntity(CompanyLegalFormDTO dto) {
        return CompanyLegalForm.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .build();
    }

    public static CompanyLegalFormDTO toDto(CompanyLegalForm companyLegalForm) {
        return CompanyLegalFormDTO.builder()
                .id(companyLegalForm.getId())
                .title(companyLegalForm.getTitle())
                .build();
    }
}
