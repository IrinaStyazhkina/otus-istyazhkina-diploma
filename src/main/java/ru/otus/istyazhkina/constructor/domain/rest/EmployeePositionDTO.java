package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePositionDTO {

    private String id;
    private String title;

    public static EmployeePosition toEntity(EmployeePositionDTO dto) {
        return EmployeePosition.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .build();
    }

    public static EmployeePositionDTO toDto(EmployeePosition position) {
        return EmployeePositionDTO.builder()
                .id(position.getId())
                .title(position.getTitle())
                .build();
    }


}
