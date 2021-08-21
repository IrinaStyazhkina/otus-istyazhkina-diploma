package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerPositionDTO {
    String id;
    String title;

    public static ManagerPosition toEntity(ManagerPositionDTO dto) {
        return ManagerPosition.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .build();
    }

    public static ManagerPositionDTO toDto(ManagerPosition managerPosition) {
        return ManagerPositionDTO.builder()
                .id(managerPosition.getId())
                .title(managerPosition.getTitle())
                .build();
    }
}
